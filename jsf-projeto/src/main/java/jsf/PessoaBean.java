package jsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import conexao.HibernateUtil;
import dao.DaoGenerico;
import model.Cidade;
import model.Estado;
import model.Pessoa;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;
import repository.IDaoPessoa;
import repository.IDaoPessoaImpl;

@Named(value = "pessoaBean")
@javax.faces.view.ViewScoped
public class PessoaBean implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoGenerico<Pessoa> daoPessoa;
	
	@Inject
	private IDaoPessoa idaoPessoa = new IDaoPessoaImpl();
	
	@Inject
	private HibernateUtil hibernateUtil;
	
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
	private List<SelectItem> estados = new ArrayList<SelectItem>();
	private List<SelectItem> cidades = new ArrayList<SelectItem>();
	private Part arquivoFoto;
	
	
	// SALVAR
	public String salvar() throws Exception{
		 
		// Setando imagem
		if(arquivoFoto != null) {
			
			byte[] imagemByte = getByte(arquivoFoto.getInputStream());
			pessoa.setFotoBase64Original(imagemByte);
			
				  //transformar em Miniatura
				  BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
				 
				  
				  int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				 
				  int largura = 200;
				  int altura = 200;
				 
				  
				  BufferedImage resizedImage = new BufferedImage(altura, altura, type);
				  Graphics2D g = resizedImage.createGraphics();
				  g.drawImage(bufferedImage, 0, 0, largura, altura, null);
				  g.dispose();
				  
				   
				  ByteArrayOutputStream baos = new ByteArrayOutputStream();
				  String extensao = arquivoFoto.getContentType().split("\\/")[1]; /*image/png*/
				  ImageIO.write(resizedImage, extensao, baos);
				  
				  String miniImagem = "data:" + arquivoFoto.getContentType() + ";base64," +
				                       DatatypeConverter.printBase64Binary(baos.toByteArray());
			 
			
			pessoa.setFotoBase64(miniImagem);
			pessoa.setExtensao(extensao);
			
			
		}	
		
		pessoa = daoPessoa.editar(pessoa);
		lista();
		mostrarMsg("Cadastrado com sucesso!!");
		return "";
	}

	// NOVO
	public String novo(){
		pessoa = new Pessoa();
		return "";
	}
	
	// EDITAR
	public String editar(){
		
		if(pessoa.getCidade() != null) {// carregar cidade
			
			List<Cidade> listaCidades = (List<Cidade>) 
					hibernateUtil.getEntityManager().createQuery("SELECT c FROM Cidade c WHERE estado.id = '"+
			pessoa.getEstado().getId()+"'")
					.getResultList();
			
			
			List<SelectItem> cidadesSelectItems = new ArrayList<SelectItem>();
			
			for(Cidade c : listaCidades) {
				cidadesSelectItems.add(new SelectItem(c,c.getNome()));
			}
			
			setCidades(cidadesSelectItems);
			System.out.println(listaCidades);
		}
		
		lista();
		return "";
	}
	
	//DELETAR
	public String deletar(){
		daoPessoa.deletar(pessoa);
		novo();
		lista();
		mostrarMsg("Deletado com sucesso!!");
		return "";
	}
	
	//LISTAR
	@PostConstruct
	public void lista(){
		listaPessoa = daoPessoa.listar(Pessoa.class,10);
	}
	
	
	// LISTAR ESTADOS
	public List<SelectItem> getEstados() {
		estados = idaoPessoa.listaEstados();
		return estados;
	}
	
	// CARREGAR CIDADES
	public void carregarCidades(AjaxBehaviorEvent event) {
		
		Estado estado = (Estado) ((SelectOneMenu) event.getSource()).getValue();
		
		if(estado != null) {
			
			 
			List<Cidade> listaCidades = (List<Cidade>) 
					hibernateUtil.getEntityManager().createQuery("SELECT c FROM Cidade c WHERE estado.id = '"+
			estado.getId()+"'")
					.getResultList();
			
			
			List<SelectItem> cidadesSelectItems = new ArrayList<SelectItem>();
			
			for(Cidade c : listaCidades) {
				cidadesSelectItems.add(new SelectItem(c,c.getNome()));
			}
			
			setCidades(cidadesSelectItems);
			System.out.println(listaCidades);
		}
		
	}
	
	// PESQUISAR CEP
	public void pesquisarCep(AjaxBehaviorEvent event){
		
		try {
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");// URL
			URLConnection buscaUrl = url.openConnection();// abrindo URL
			InputStream respUrl = buscaUrl.getInputStream();// resultado URL
			BufferedReader br = new BufferedReader(new InputStreamReader(respUrl,"UTF-8"));// resultado Json
			
			// jogando Json em uma String
			StringBuilder jsonCep = new StringBuilder();
			
			String aux = "";
			while((aux = br.readLine()) != null) {
				jsonCep.append(aux);
			}
			
			// jogando para dentro da classe
			Pessoa pAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setLocalidade(pAux.getLocalidade());
			pessoa.setLogradouro(pAux.getLogradouro());
			pessoa.setBairro(pAux.getBairro());
			pessoa.setUf(pAux.getUf());
			
			System.out.println(jsonCep);
			
		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("CEP invalido");
		}
	}
	
	
	// MOSTRAR MENSAGEM
	private void mostrarMsg(String msg) {
		 FacesContext contexto = FacesContext.getCurrentInstance();//pagina
		 FacesMessage mensagem = new FacesMessage(msg);// mensagem da pagina
		 contexto.addMessage(null,mensagem);// adicionando
	}
	
	
	//LOGAR
	public String logar(){
		
		 
		Pessoa p = idaoPessoa.consultaPessoa(pessoa.getLogin(), pessoa.getSenha());
		
		if(p.getNome() != null) {
			
			// adicionar na sessao
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			
			external.getSessionMap().put("usuarioLogado", p);
			
			return "primeirapagina.jsf";
		}else {
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Usuario não encontrado!!"));
			return "index.jsf";

		}
				
	}
	
	// DESLOGAR
	public String deslogar(){
		
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		external.getSessionMap().remove("usuarioLogado");
		
		HttpServletRequest request = (HttpServletRequest) 
				context.getCurrentInstance().getExternalContext().getRequest();
		
		request.getSession().invalidate();
		
		
		return "index.jsp";
	}
	
	// PERMITE ACESSO
	public Boolean permiteAcesso(String perfilAcesso){
		
		// adicionar na sessao
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		
	 	Pessoa p =(Pessoa) external.getSessionMap().get("usuarioLogado");
	  
	 	return p.getPerfil().equals(perfilAcesso);
	 
	}
	

	// DOWNLOAD IMAGEM
	public void downloadImagem() throws Exception{
		
		// Parametros JSF
		Map<String, String> parametros = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		
		String pessoaId = parametros.get("pessoaId");
		
		Pessoa p = new Pessoa();
		p = daoPessoa.pesquisar(Long.parseLong(pessoaId),p);
		
		// Jogando download para tela
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=download." + pessoa.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(p.getFotoBase64Original().length);
		response.getOutputStream().write(p.getFotoBase64Original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();

	}
	
	
	
	// CONVERSOR INPUTSTREAM PARA BYTES
	private byte[] getByte(InputStream is) throws Exception{
		
		int len;
		int size = 1024;
		byte[] buf = null;
		
		if (is instanceof ByteArrayInputStream){
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		}else {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			
			while ((len = is.read(buf, 0, size)) != -1){
				bos.write(buf, 0, len);
			}
			
			buf = bos.toByteArray();
			
		}
		
		return buf;
	}
	
	
	
	
	
	
	// GET E SET
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGenerico<Pessoa> getDaoPessoa() {
		return daoPessoa;
	}

	public void setDaoPessoa(DaoGenerico<Pessoa> daoPessoa) {
		this.daoPessoa = daoPessoa;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public Part getArquivoFoto() {
		return arquivoFoto;
	}

	public void setArquivoFoto(Part arquivoFoto) {
		this.arquivoFoto = arquivoFoto;
	}

	
	
}
