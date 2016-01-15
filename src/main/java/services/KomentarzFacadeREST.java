/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.RejestracjaController;
import beansDB.GraBean;
import beansDB.KomenatrzBean;
import entitys.Dzial;
import entitys.Gra;
import entitys.Komentarz;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Marcin
 */
@Stateless
@Path("komentarz")
public class KomentarzFacadeREST extends AbstractFacade<Komentarz> {

    @PersistenceContext( unitName = "WebApplication1PU")
    private EntityManager em;

    @EJB
    private KomenatrzBean komenatrzBean;
    public KomentarzFacadeREST() {
        super(Komentarz.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Komentarz entity) {
        super.create(entity);
    }
    @POST
    @Path("dodajKomentarz/{dzial}/{idDzialu}")
    @Consumes({"application/json"})
    public Response dodajKomentarz(@PathParam("dzial")Dzial dzial,@PathParam("idDzialu")Long idDzialu,Komentarz entity) {

        entity.setDzial(dzial);
        entity.setIdDzialu(idDzialu);
        if(dzial==Dzial.NEWS)
            entity.setData(new Date());
        else
            entity.setData(new Date());
        komenatrzBean.dodaj(entity);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Long id, Komentarz entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Komentarz find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Komentarz> findAll() {
        return super.findAll();
    }

    @GET
    @Path("/findAll/{dzial}/{idDzialu}")
    @Produces({"application/json"})
    public List<Komentarz> findAllByDzialAndId(@PathParam("dzial")Dzial dzial,@PathParam("idDzialu")Long idDzialu) {
        return komenatrzBean.getAllByDzial(dzial, idDzialu);
    }
    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Komentarz> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
