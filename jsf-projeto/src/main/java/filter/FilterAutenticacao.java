package filter;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import conexao.HibernateUtil;
import model.Pessoa;

@WebFilter("/*")
public class FilterAutenticacao extends HttpFilter implements Filter,Serializable {
 
	private static final long serialVersionUID = 1L;
	
	@Inject
	private HibernateUtil hibernateUtil;
	
    public FilterAutenticacao() {
        super();
    }

	public void destroy() {
		hibernateUtil.getEntityManager().close();;
	}
 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		Pessoa p = (Pessoa) session.getAttribute("usuarioLogado");
		
		//String url = req.getServletPath();
		
		if(p == null) {
			RequestDispatcher redirecionar = req.getRequestDispatcher("/index.jsf");
			redirecionar.forward(request, response);
			return;
		}else {
			chain.doFilter(request, response);
		}
		
		
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		hibernateUtil.getEntityManager();
	}

}
