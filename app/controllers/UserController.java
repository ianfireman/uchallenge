package controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;


public class UserController extends Controller {
	
	private JPAApi jpaApi;

	@Inject
	public UserController(JPAApi api) {
	  this.jpaApi = api;
	}
	
	@Transactional
	public Result index() {
		Query result = jpaApi.em().createNativeQuery("SELECT * FROM User;");
		ObjectNode response = Json.newObject();
		ArrayNode arrayNode = response.putArray("body");
        List<User> resultList = null;
        try {
            resultList = result.getResultList();
            
            JsonNode jsonUsers = Json.toJson(resultList);
            for (JsonNode user : jsonUsers) {
            	int index = 0;
        		ObjectNode localUser = Json.newObject();
            	for (JsonNode param : user) {
            		Logger.debug(user.toString());
            		switch (index){
            		case 0:
            			localUser.put("id", param);
            			break;
            		case 1:
            			localUser.put("name", param);
            			break;
            		case 2:
            			localUser.put("email", param);
            			break;
            		case 4:
            			localUser.put("telefone", param);
            			break;
            		case 5:
            			localUser.put("created", param);
            			break;
            		case 6:
            			localUser.put("modified", param);
            			break;
            		}
            		index++;
            	}
            	arrayNode.add(localUser);
            }
            return ok(response);
        } catch (NoResultException ex) {
        	Logger.info(ex.getMessage());
        	response.put("mensagem", "Nenhum resultado encontrado");
            return badRequest(response);
        } catch (NullPointerException e){
        	Logger.error(e.getMessage());
        	response.put("mensagem", "Algum erro ocorreu");
    		return badRequest(response);
        }
    }
	
	@Transactional
	public Result create() {
		JsonNode json = request().body().asJson();
		ObjectNode response = Json.newObject();
	    if(json == null) {
	    	response.put("message", "Expecting Json data");
        	return badRequest(response);
	    } else {
	        String name = json.findPath("name").textValue();
	        String email = json.findPath("email").textValue();
	        String password = json.findPath("password").textValue();
	        String telefone = json.findPath("telefone").textValue();
	        if(name == null || email == null || password == null || telefone == null) {
	        	response.put("message", "Parametros faltando");
	        	return badRequest(response);
	        } else {
	        	Logger.info("Entered");
	        	User newestUser = new User();
	        	newestUser.setEmail(email);
	        	newestUser.setName(name);
	        	newestUser.setPassword(password);
	        	newestUser.setTelefone(telefone);
	        	newestUser.setModified(new Date());
	        	newestUser.setCreated(new Date());
        	    try{
        	    	jpaApi.em().persist(newestUser);
        	    }
        	    catch(PersistenceException e){
        	        String message = e.getCause().getCause().getMessage();
        	        String[] parts = message.split(" ");
        	        if(parts[0].equals("Duplicate")){
        	        	response.put("message", "E-mail ja existente");
        	        	return badRequest(response);
        	        }
        	    }
        	    JsonNode jsonUser = Json.toJson(newestUser);
	            return ok(jsonUser);
	        }
	    }
    }
	
	@Transactional
	public Result update(String id) {
		JsonNode json = request().body().asJson();
		ObjectNode response = Json.newObject();
		if(json == null) {
			response.put("message", "Expecting Json data");
        	return badRequest(response);
	    } else {
			Query q = jpaApi.em().createQuery("select f from User f where f.id="+id, User.class);
	 
	        User user = null;
	        try {
	        	user = (User) q.getSingleResult();
	        } catch (NoResultException ex) {
	        	response.put("message", "Usuario invalido");
	        	return badRequest(response);
	        } catch (NullPointerException e){
	        	response.put("message", "Usuario invalido");
	    		return badRequest(response);
	        }
	        String name = json.findPath("name").textValue();
	        String email = json.findPath("email").textValue();
	        String password = json.findPath("password").textValue();
	        String telefone = json.findPath("telefone").textValue();
	        if(name == null || email == null || password == null || telefone == null) {
	        	response.put("message", "Parametros faltando");
	        	return badRequest(response);
	        } else {
	        	if(emailIsValid(email)){
		        	Logger.info("Entered");
		        	user.setEmail(email);
		        	user.setName(name);
		        	user.setPassword(password);
		        	user.setTelefone(telefone);
		        	user.setModified(new Date());
	        	    try{
	        	    	jpaApi.em().merge(user);
	        	    	Logger.debug(user.getModified().toString());
	        	    }
	        	    catch(PersistenceException e){
	        	        String message = e.getCause().getCause().getMessage();
	        	        String[] parts = message.split(" ");
	        	        if(parts[0].equals("Duplicate")){
	        	        	response.put("message", "E-mail ja existente");
	        	        	return badRequest(response);
	        	        }
	        	    }
	        	    JsonNode jsonUser = Json.toJson(user);
		            return ok(jsonUser);
	        	} else {
	        		response.put("message", "E-mail ja existente");
    	        	return badRequest(response);
	        	}
	        }
	    }
    }
	
	public boolean emailIsValid(String email){
		Query q = jpaApi.em().createQuery("select f from User f where f.email='"+email+"'", User.class);
		 
        User user = null;
        try {
        	user = (User) q.getSingleResult();
        } catch (NoResultException ex) {
        	return true;
        } catch (NullPointerException e){
        	return false;
        }
		return false;
	}

}
