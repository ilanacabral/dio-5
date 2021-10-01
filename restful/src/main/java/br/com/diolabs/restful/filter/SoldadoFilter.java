package br.com.diolabs.restful.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SoldadoFilter implements  Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
                //Encapsula uma requsição http
                HttpServletRequest request = (HttpServletRequest)servletRequest;

                //Obtem enumeração com todas as chaves de informações presentes no cabeçalho               
                Enumeration<String> enumHeader = request.getHeaderNames();
                
                //Obtém stream da lista criada apartir da enumeração de chaves do cabeçalho
                Stream<String> streamHeader = Collections.list(enumHeader).stream();
                
                //Cria um mapa com chave/valor correpondente as informações do cabeçalho
                Map<String,String> mapHeader =  streamHeader.collect(Collectors.toMap(m ->m,request::getHeader));
               
               //Obtém chave de autorização do mapa
               String chaveAutho = mapHeader.get("authorization");

               //outra forma de fazer, é pegar diretamente no request a chave que precisa
               //chaveAutho = request.getHeader("authorization");
               
               //Montando a resposta
                HttpServletResponse response = (HttpServletResponse)servletResponse;

                //Verifica se é o esperado
                if (chaveAutho!=null && !chaveAutho.equals("BATATINHA")){
                     response.sendError(403);
                }else{
                    filterChain.doFilter(request, response);
                }

             
        
    }   
    
    
}
