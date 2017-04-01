package ch.pproject.vms.server.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.commons.authentication.DevelopmentAccessController;
import org.eclipse.scout.rt.server.commons.authentication.ServiceTunnelAccessTokenAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController;
import org.eclipse.scout.rt.server.commons.authentication.TrivialAccessController.TrivialAuthConfig;

import ch.pproject.vms.server.core.security.UserStoreAccessController;
import ch.pproject.vms.server.core.security.UserStoreAccessController.UserStoreConfig;

/**
 * <h3>{@link ServerServletFilter}</h3> This is the main server side servlet filter.
 *
 * @author pluessth
 */
public class ServerServletFilter implements Filter {

  private TrivialAccessController m_trivialAccessController;
  private ServiceTunnelAccessTokenAccessController m_tunnelAccessController;
  private UserStoreAccessController m_userStoreAccessController;
  private DevelopmentAccessController m_developmentAccessController;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    m_trivialAccessController = BEANS.get(TrivialAccessController.class).init(new TrivialAuthConfig().withExclusionFilter(filterConfig.getInitParameter("filter-exclude")));
    m_tunnelAccessController = BEANS.get(ServiceTunnelAccessTokenAccessController.class).init();
    m_userStoreAccessController = BEANS.get(UserStoreAccessController.class).init(new UserStoreConfig());
    m_developmentAccessController = BEANS.get(DevelopmentAccessController.class).init();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse resp = (HttpServletResponse) response;

    if (m_trivialAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_tunnelAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_userStoreAccessController.handle(req, resp, chain)) {
      return;
    }

    if (m_developmentAccessController.handle(req, resp, chain)) {
      return;
    }

    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  @Override
  public void destroy() {
    m_developmentAccessController.destroy();
    m_tunnelAccessController.destroy();
    m_userStoreAccessController.destroy();
    m_trivialAccessController.destroy();
  }
}
