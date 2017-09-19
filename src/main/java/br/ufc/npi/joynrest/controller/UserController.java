package br.ufc.npi.joynrest.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufc.npi.joynrest.config.JwtEvaluator;
import br.ufc.npi.joynrest.model.Atividade;
import br.ufc.npi.joynrest.model.CodigoCapturado;
import br.ufc.npi.joynrest.model.Convite;
import br.ufc.npi.joynrest.model.Evento;
import br.ufc.npi.joynrest.model.Papel;
import br.ufc.npi.joynrest.model.ParticipacaoAtividade;
import br.ufc.npi.joynrest.model.ParticipacaoEvento;
import br.ufc.npi.joynrest.model.TiposAtividades;
import br.ufc.npi.joynrest.model.Usuario;
import br.ufc.npi.joynrest.response.AuthToken;
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
import io.undertow.util.BadRequestException;

@RestController
public class UserController {

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
	
	@RequestMapping(value = "/usuario/{eventoId}")
	@ResponseBody
	public UsuarioData usuario(@PathVariable Long eventoId) throws ServletException{
		Usuario u = jwtEvaluator.usuarioToken();
		ParticipacaoEvento peEvento = peService.getParticipacaoEvento(u.getId(), eventoId);
		return new UsuarioData(u.getId(), u.getNome(), u.getEmail(), u.getKeyFacebook(), u.getPapel(), peEvento.getPontos());
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

		return new MensagemRetorno("Usuario cadastrado");

	}
	
	@RequestMapping(value = "/resgatarqrcode",  method = RequestMethod.POST)
	@ResponseBody
	public MensagemRetorno resgatarQrCode(@RequestBody QrCode qrcode) throws BadRequestException, ServletException {
		String codigo = qrcode.getCodigo();
		Atividade atividade = atividadeService.getAtividade(codigo);
		Evento evento = atividade.getEvento();
		Usuario usuarioLogado = jwtEvaluator.usuarioToken();
		ParticipacaoAtividade participacaoAtividade = null;
		
		if(paService.verificarParticipacaoAtividade(usuarioLogado, atividade) == false)
			participacaoAtividade = paService.adicionarAtividade(usuarioLogado, atividade, Papel.PARTICIPANTE);
		else
			participacaoAtividade = paService.getParticipacaoAtividade(usuarioLogado.getId(), atividade.getId());
		
		for(CodigoCapturado c : participacaoAtividade.getCodigos()){
			if(c.getCodigo().equals(codigo)) return new MensagemRetorno("Esse codigo ja foi resgatado");
		}
		
		ParticipacaoEvento participacaoEvento = peService.getParticipacaoEvento(usuarioLogado.getId(), evento.getId());
		if(atividade.getTipo() == TiposAtividades.CHECKIN && codigo.equals(atividade.getCodeCheckin())){
			addCodigoCapturado(codigo, participacaoAtividade);
			computarPontos(participacaoEvento, atividade);
			return new MensagemRetorno("Codigo resgatado, " + atividade.getPontuacao() + " pontos resgatados");
		} else if(atividade.getTipo() == TiposAtividades.CHECKIN_CHECKOUT){
			if(codigo.equals(atividade.getCodeCheckin())){
				addCodigoCapturado(codigo, participacaoAtividade);
				return new MensagemRetorno("Codigo resgatado. Esperando codigo de checkout");
			} else if(codigo.equals(atividade.getCodeCheckout())){
				for(CodigoCapturado c : participacaoAtividade.getCodigos()){
					if(c.getCodigo().equals(atividade.getCodeCheckin())){
						computarPontos(participacaoEvento, atividade);
						addCodigoCapturado(codigo, participacaoAtividade);
						return new MensagemRetorno("Codigo resgatado, " + atividade.getPontuacao() + " pontos resgatados");
					}
						
				}
			}
		}
		
		throw new BadRequestException("Codigo invalido");
		
	}
	
	private void addCodigoCapturado(String codigo, ParticipacaoAtividade participacaoAtividade){
		CodigoCapturado codigoCapturado = new CodigoCapturado();
		codigoCapturado.setAtividade(participacaoAtividade);
		codigoCapturado.setCodigo(codigo);
		participacaoAtividade.getCodigos().add(codigoCapturado);
		codigoCapturadoService.salvar(codigoCapturado);
		paService.atualizarParticipacaoAtividade(participacaoAtividade);
	}
	
	private void computarPontos(ParticipacaoEvento participacaoEvento, Atividade atividade){
		participacaoEvento.setPontos(participacaoEvento.getPontos() + atividade.getPontuacao());
		peService.atualizarParticipacaoEvento(participacaoEvento);
	}
	
	@RequestMapping(value = "/testetoken",  method = RequestMethod.POST)
	@ResponseBody
	public MensagemRetorno testarToken(@RequestBody AuthToken authToken){
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
        
        return new MensagemRetorno("Token invalido");
	}
	
}






























