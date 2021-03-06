package br.ufc.npi.joynrest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.ufc.npi.joynrest.config.JwtEvaluator;
import br.ufc.npi.joynrest.exceptions.CapturaException;
import br.ufc.npi.joynrest.model.AccountCredentials;
import br.ufc.npi.joynrest.model.Atividade;
import br.ufc.npi.joynrest.model.CodigoCapturado;
import br.ufc.npi.joynrest.model.CodigosTurno;
import br.ufc.npi.joynrest.model.Convite;
import br.ufc.npi.joynrest.model.Evento;
import br.ufc.npi.joynrest.model.Papel;
import br.ufc.npi.joynrest.model.ParticipacaoAtividade;
import br.ufc.npi.joynrest.model.ParticipacaoEvento;
import br.ufc.npi.joynrest.model.TipoCodigo;
import br.ufc.npi.joynrest.model.TiposAtividades;
import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.response.AuthToken;
import br.ufc.npi.joynrest.response.FacebookDados;
import br.ufc.npi.joynrest.response.ItemRanking;
import br.ufc.npi.joynrest.response.MensagemResgate;
import br.ufc.npi.joynrest.response.MensagemRetorno;
import br.ufc.npi.joynrest.response.QrCode;
import br.ufc.npi.joynrest.response.UsuarioData;
import br.ufc.npi.joynrest.service.AtividadeService;
import br.ufc.npi.joynrest.service.CodigoCapturadoService;
import br.ufc.npi.joynrest.service.CodigoService;
import br.ufc.npi.joynrest.service.ConviteService;
import br.ufc.npi.joynrest.service.EventoService;
import br.ufc.npi.joynrest.service.ParticipacaoAtividadeService;
import br.ufc.npi.joynrest.service.ParticipacaoEventoService;
import br.ufc.npi.joynrest.service.UsuarioService;
import br.ufc.npi.joynrest.util.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.undertow.util.BadRequestException;

@RestController
public class JoynController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	EventoService eventoService;
	
	@Autowired
	AtividadeService atividadeService;
	
	@Autowired
	ParticipacaoEventoService peService;
	
	@Autowired
	ParticipacaoAtividadeService paService;
	
	@Autowired
	CodigoCapturadoService codigoCapturadoService;
	
	@Autowired
	CodigoService codigoService;
	
	@Autowired
	ConviteService conviteService;
	
	@Autowired
	JwtEvaluator jwtEvaluator;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	private static final long EXPIRATION_TIME = 1000 * Constants.TOKEN_EXPIRAR_MINUTOS;
	
	@RequestMapping(value = "/usuario/{eventoId}")
	@ResponseBody
	public UsuarioData usuario(@PathVariable Long eventoId) throws ServletException{
		Usuario u = jwtEvaluator.usuarioToken();
		ParticipacaoEvento peEvento = peService.getParticipacaoEvento(u.getId(), eventoId);
		return new UsuarioData(u.getId(), u.getNome(), u.getEmail(), u.getKeyFacebook(), u.getPapel(), peEvento!=null?peEvento.getPontos():0);
	}
	
	@RequestMapping(value = "/logar")
	@ResponseBody
	public AuthToken logar(@RequestBody AccountCredentials usuario){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				usuario.getUsername(), 
				usuario.getPassword(), 
				Collections.emptyList()
		));
		String JWT = Jwts.builder()
				.setSubject(authentication.getName())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.CHAVE_SECRETA)
				.compact();
		
		return new AuthToken(Constants.TOKEN_PREFIX + " " + JWT);
		
	}
	
	@RequestMapping(path = "/cadastrar",  method = RequestMethod.POST)
	public MensagemRetorno cadastrar(@RequestBody Usuario usuario) {
		usuario.setPapel(Papel.USUARIO);
		Usuario userBanco = usuarioService.salvarUsuario(usuario);
		Convite convite = conviteService.getConvite(usuario.getEmail());
		if(convite != null){
			Evento eventoConvidado = eventoService.buscarEvento(convite.getIdEvento());
			ParticipacaoEvento pe = new ParticipacaoEvento(userBanco, eventoConvidado, Papel.ORGANIZADOR, true);
			peService.addParticipacaoEvento(pe);
		}

		return new MensagemRetorno("Usuário cadastrado");

	}
	
	@RequestMapping(value = "/logarfacebook")
	@ResponseBody
	public AuthToken logar(@RequestBody FacebookDados facebookDados) throws AuthenticationException{
		String keyFacebook = facebookDados.getKeyFacebook();
		
		if(keyFacebook == null || keyFacebook.isEmpty()) throw new AuthenticationException("Chave do Facebook nula");
		
		Usuario usuarioFacebook = usuarioService.getUsuarioKeyFacebook(keyFacebook);
		if(usuarioFacebook == null){
			Usuario usuarioBase = usuarioService.getUsuario(facebookDados.getEmail());
			if(usuarioBase == null){
				usuarioFacebook = new Usuario(facebookDados.getNome(), facebookDados.getFoto64(), keyFacebook, facebookDados.getEmail(), keyFacebook, Papel.USUARIO);
				usuarioFacebook = usuarioService.salvarUsuario(usuarioFacebook);
			}else{
				throw new AuthenticationException("O email do Facebook já está cadastrado");
			}
		}
		
		String JWT = Jwts.builder()
				.setSubject(usuarioFacebook.getEmail())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.CHAVE_SECRETA)
				.compact();
		
		return new AuthToken(Constants.TOKEN_PREFIX + " " + JWT);
		
	}
	
	@RequestMapping(value = "/resgatarqrcode",  method = RequestMethod.POST)
	@ResponseBody
	public MensagemResgate resgatarQrCode(@RequestBody QrCode qrcode) throws BadRequestException, ServletException, CapturaException {
		String codigo = qrcode.getCodigo();
		Atividade atividade = atividadeService.getAtividade(codigo);
		Evento evento = atividade.getEvento();
		Usuario usuarioLogado = jwtEvaluator.usuarioToken();
		ParticipacaoAtividade participacaoAtividade = recuperarParticipacaoAtividade(usuarioLogado, atividade);
		ParticipacaoEvento participacaoEvento = recuperarParticipacaoEvento(usuarioLogado, evento);
		
		if(codigoJaCapturado(participacaoAtividade, codigo))
			return new MensagemResgate("Esse código já foi resgatado", participacaoEvento.getPontos());
		
		if(atividade.getTipo() == TiposAtividades.CHECKIN){
			for(CodigosTurno cturno : atividade.getCodigosTurno()){
				if(codigo.equals(cturno.getCodigoCheckin())){
					addCodigoCapturado(codigo, participacaoAtividade, TipoCodigo.CHECKIN);
					if(temMinFreqAtv(atividade, participacaoAtividade)){
						computarPontos(participacaoEvento, atividade);
						return new MensagemResgate("Código resgatado, " + atividade.getPontuacao() + " pontos resgatados. Você completou o mínimo de frequência para a atividade " + atividade.getNome(), participacaoEvento.getPontos());
					}else{
						return new MensagemResgate("Código resgatado", participacaoEvento.getPontos());
					}
				}
			}
		} 

		
		if(atividade.getTipo() == TiposAtividades.CHECKIN_CHECKOUT){
			for(CodigosTurno cturno : atividade.getCodigosTurno()){
				if(codigo.equals(cturno.getCodigoCheckin())){
					addCodigoCapturado(codigo, participacaoAtividade, TipoCodigo.CHECKIN);
					return new MensagemResgate("Código resgatado. Esperando código de checkout", participacaoEvento.getPontos());
				} else if(codigo.equals(cturno.getCodigoCheckout())){
					for(CodigoCapturado cc : participacaoAtividade.getCodigosCapturados()){
						if(cc.getCodigo().equals(cturno.getCodigoCheckin())){
							addCodigoCapturado(codigo, participacaoAtividade, TipoCodigo.CHECKOUT);
							if(temMinFreqAtv(atividade, participacaoAtividade)){
								computarPontos(participacaoEvento, atividade);
								return new MensagemResgate("Código resgatado, " + atividade.getPontuacao() + " pontos resgatados. Você completou o mínimo de frequência para a atividade " + atividade.getNome(), participacaoEvento.getPontos());
							}else{
								return new MensagemResgate("Código resgatado", participacaoEvento.getPontos());
							}
						}
					}
					
					throw new CapturaException("Capture o código de checkin antes do código de checkout");
				}
			}
		}
		
		throw new BadRequestException("Código invalido");
		
	}
	
	private ParticipacaoAtividade recuperarParticipacaoAtividade(Usuario usuario, Atividade atividade){
		if(paService.verificarParticipacaoAtividade(usuario, atividade) == false)
			return paService.adicionarAtividade(usuario, atividade, Papel.PARTICIPANTE);
		else
			return paService.getParticipacaoAtividade(usuario.getId(), atividade.getId());
	}
	
	private ParticipacaoEvento recuperarParticipacaoEvento(Usuario usuario, Evento evento){
		if(peService.verificarParticipacaoEvento(usuario, evento) == false)
			return peService.addParticipacaoEvento(new ParticipacaoEvento(usuario, evento, Papel.PARTICIPANTE, true));
		else
			return peService.getParticipacaoEvento(usuario.getId(), evento.getId());
	}
	
	private boolean codigoJaCapturado(ParticipacaoAtividade participacaoAtividade, String codigo){
		for(CodigoCapturado c : participacaoAtividade.getCodigosCapturados()){
			if(c.getCodigo().equals(codigo)) return true;
		}
		return false;
	}
	
	private void addCodigoCapturado(String codigo, ParticipacaoAtividade participacaoAtividade, TipoCodigo tipo){
		CodigoCapturado codigoCapturado = new CodigoCapturado();
		codigoCapturado.setAtividade(participacaoAtividade);
		codigoCapturado.setCodigo(codigo);
		codigoCapturado.setTipo(tipo);
		participacaoAtividade.getCodigosCapturados().add(codigoCapturado);
		codigoCapturadoService.salvar(codigoCapturado);
		paService.atualizarParticipacaoAtividade(participacaoAtividade);
	}
	
	private void computarPontos(ParticipacaoEvento participacaoEvento, Atividade atividade){
		participacaoEvento.setPontos(participacaoEvento.getPontos() + atividade.getPontuacao());
		peService.atualizarParticipacaoEvento(participacaoEvento);
	}
	
	private boolean temMinFreqAtv(Atividade atividade, ParticipacaoAtividade pa){
		if(atividade.getTipo() == TiposAtividades.CHECKIN){
			return pa.getCodigosCapturados().size() == atividade.getMinimoParaFreq();
		}else{
			int qtCheckouts = 0;
			for(CodigoCapturado cc : pa.getCodigosCapturados()){
				if(cc.getTipo() == TipoCodigo.CHECKOUT)
					qtCheckouts += 1;
			}
			return qtCheckouts == atividade.getMinimoParaFreq();
		}
	}
	
	@RequestMapping(value = "/testetoken",  method = RequestMethod.POST)
	@ResponseBody
	public MensagemRetorno testarToken(@RequestBody AuthToken authToken) throws BadRequestException{
		String token = authToken.getToken();
        if (token != null) {
			String email = Jwts.parser()
						.setSigningKey(Constants.CHAVE_SECRETA)
						.parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
				if(usuarioService.getUsuario(email) != null)
					return new MensagemRetorno("Token valido");
        }
        
        throw new BadRequestException("Token invalido");
	}
	
	@RequestMapping(value = "/ranking/{eventoId}")
	@ResponseBody
	public List<ItemRanking> gerarRanking(@PathVariable Long eventoId){
		Evento evento = eventoService.buscarEvento(eventoId);
		
		List<ItemRanking> ranking = new ArrayList<>();
		for(ParticipacaoEvento pe : evento.getParticipantes()){
			if(pe.getPapel() == Papel.PARTICIPANTE)
				ranking.add(new ItemRanking(pe.getUsuario().getNome(), pe.getPontos()));
		}
		
		Collections.sort(ranking, new Comparator<ItemRanking>() {
	        @Override
	        public int compare(ItemRanking item2, ItemRanking item1)
	        {
	            if (item2.getPontos() > item1.getPontos()) return -1;
	            else if (item2.getPontos() < item1.getPontos()) return 1;
	            else return 0;
	        }
	    });
		
		for(int i = 0; i < ranking.size(); i++)
			ranking.get(i).setColocacao(i+1);
		
		return ranking;
	}
	
}






























