package com.example.chris.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "fillUpApi",
        version = "v1",
        resource = "fillUp",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.chris.example.com",
                ownerName = "backend.myapplication.chris.example.com",
                packagePath = ""
        )
)
public class FillUpEndpoint {

    private static final Logger logger = Logger.getLogger(FillUpEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(FillUp.class);
    }

    /**
     * Returns the {@link FillUp} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code FillUp} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "fillUp/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public FillUp get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting FillUp with ID: " + id);
        FillUp fillUp = ofy().load().type(FillUp.class).id(id).now();
        if (fillUp == null) {
            throw new NotFoundException("Could not find FillUp with ID: " + id);
        }
        return fillUp;
    }

    /**
     * Inserts a new {@code FillUp}.
     */
    @ApiMethod(
            name = "insert",
            path = "fillUp",
            httpMethod = ApiMethod.HttpMethod.POST)
    public FillUp insert(FillUp fillUp) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that fillUp.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(fillUp).now();
        logger.info("Created FillUp with ID: " + fillUp.getId());

        return ofy().load().entity(fillUp).now();
    }

    /**
     * Updates an existing {@code FillUp}.
     *
     * @param id     the ID of the entity to be updated
     * @param fillUp the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code FillUp}
     */
    @ApiMethod(
            name = "update",
            path = "fillUp/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public FillUp update(@Named("id") Long id, FillUp fillUp) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(fillUp).now();
        logger.info("Updated FillUp: " + fillUp);
        return ofy().load().entity(fillUp).now();
    }

    /**
     * Deletes the specified {@code FillUp}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code FillUp}
     */
    @ApiMethod(
            name = "remove",
            path = "fillUp/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(FillUp.class).id(id).now();
        logger.info("Deleted FillUp with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "fillUp",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<FillUp> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<FillUp> query = ofy().load().type(FillUp.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<FillUp> queryIterator = query.iterator();
        List<FillUp> fillUpList = new ArrayList<FillUp>(limit);
        while (queryIterator.hasNext()) {
            fillUpList.add(queryIterator.next());
        }
        return CollectionResponse.<FillUp>builder().setItems(fillUpList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(FillUp.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find FillUp with ID: " + id);
        }
    }
}