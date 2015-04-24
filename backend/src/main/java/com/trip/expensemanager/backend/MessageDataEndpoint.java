package com.trip.expensemanager.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "messageDataApi",
        version = "v1",
        resource = "messageData",
        namespace = @ApiNamespace(
                ownerDomain = "backend.expensemanager.trip.com",
                ownerName = "backend.expensemanager.trip.com",
                packagePath = ""
        )
)
public class MessageDataEndpoint {

    private static final String ACCOUNT_SID = "ACbc4d01a5ed864c080ccb7fd0c797ffda";
    private static final String AUTH_TOKEN = "7256d39d8b87cda3a433eb84fd02fb1a";
    private static final String MOBILE_NUMBER = "+19284152141";


    private static final Logger log = Logger.getLogger(GCMUtil.class.getName());

    private static final Logger logger = Logger.getLogger(MessageDataEndpoint.class.getName());

    /**
     * This method gets the <code>MessageData</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>MessageData</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getMessageData")
    public MessageData getMessageData(@Named("id") Long id) {
        EntityManager mgr = getEntityManager();
        MessageData messageData = null;
        try {
            messageData = mgr.find(MessageData.class, id);
        } finally {
            mgr.close();
        }
        return messageData;
    }

    @ApiMethod(name = "insertMessageData")
    public MessageData insertMessageData(MessageData messageData) throws TwilioRestException{
        EntityManager mgr = getEntityManager();
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            Random random = new Random();
            String strToken = String.valueOf(random.nextInt(90000)+10000);
            messageData.setToken(strToken);

            // Build a filter for the MessageList
            params.add(new BasicNameValuePair("Body", "Your verification code is "+strToken));
            params.add(new BasicNameValuePair("To", messageData.getMobNo()));
            params.add(new BasicNameValuePair("From", MOBILE_NUMBER));

            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);

            log.info("Msg send with id "+ message.getSid());

            mgr.persist(messageData);
        } finally {
            mgr.close();
        }
        return messageData;
    }

    @ApiMethod(name = "updateMessageData")
    public MessageData updateMessageData(MessageData messageData) {
        EntityManager mgr = getEntityManager();
        try {
            if (!containsMessageData(messageData)) {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(messageData);
        } finally {
            mgr.close();
        }
        return messageData;
    }

    private boolean containsMessageData(MessageData messageData) {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try {
            MessageData item = mgr.find(MessageData.class, messageData.getId());
            if (item == null) {
                contains = false;
            }
        } finally {
            mgr.close();
        }
        return contains;
    }

    private static EntityManager getEntityManager() {
        return EMF.get().createEntityManager();
    }
}