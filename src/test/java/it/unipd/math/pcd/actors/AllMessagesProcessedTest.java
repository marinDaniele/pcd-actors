package it.unipd.math.pcd.actors;

import it.unipd.math.pcd.actors.utils.MyTestActorSystem;
import it.unipd.math.pcd.actors.utils.actors.MyTestActor;
import it.unipd.math.pcd.actors.utils.actors.TrivialActor;
import it.unipd.math.pcd.actors.utils.messages.TrivialMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Daniele Marin on 04/02/16.
 * Test che tutti i messaggi vengano processati dall'actor
 */
public class AllMessagesProcessedTest {
    private ActorSystem system;

    /**
     * Initializes the {@code system} with a concrete implementation before each test.
     */
    @Before
    public void init() {
        system = new MyTestActorSystem();
    }

    @Test
    public void shouldCreateAnActorRefWithActorOfTest() {
        ActorRef ref = system.actorOf(TrivialActor.class);
        Assert.assertNotNull("A reference was created and it is not null", ref);
    }


    // test che verifica che l'attore processi tutti i messaggi
    @Test
    public void actoreProcessAllMessages() {
        ActorRef ref1 = system.actorOf(MyTestActor.class);

        Actor act = ((MyTestActorSystem)system).giveMeActor(ref1);

        ((MyTestActor)act).setRefAs((AbsActorSystem)system);

        for (int i = 0; i < 2000; i++) {
            ref1.send(new TrivialMessage(), ref1);
        }

        system.stop(ref1);


        int sendM = ((MyTestActorSystem)system).getNumSendMessages();
        int reciveM = ((MyTestActorSystem)system).getRecivedMessage();

        Assert.assertEquals("devono essere processati tutti i messaggi",sendM,reciveM);

    }
}
