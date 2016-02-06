package it.unipd.math.pcd.actors.utils;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import it.unipd.math.pcd.actors.utils.actors.MyTestActor;

/**
 * Created by Daniele Marin on 04/02/16.
 */
public class MyTestActorSystem extends BasicActorSystem {


    //-----------------------------
    // solo per test
    private volatile int counterOfRecived=0;

    public void incReciveMessages() { counterOfRecived++; }

    public int getRecivedMessage() { return counterOfRecived; }

    public int getNumSendMessages() { return numSendMessages; }

    @Override
    public void stop(ActorRef<?> actor) {

        if(actors.containsKey(actor)) {
            // recupero l'Actor relativo al ActorRef
            AbsActor attore = (AbsActor) giveMeActor(actor);

            if (attore.isActive()) {

                synchronized (attore) {
                    // Disattivo l'Actor
                    attore.deactiveActor();

                    // attendo che l'attore processi tutti i messaggi
                    while (!attore.haveFinished()) {
                        try {
                            // aspetto che l'attore abbia terminato di processare i messaggi
                            attore.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    /**
                     * Comando che incrementa la variabile utilizzata nel test AllMessagesProcessedTest
                     * Decommentarlo per effettuare il test
                     */
                    numSendMessages = ((MyTestActor) attore).getNumeroMessaggiInviati();


                    // Rimuovo L'Actor dal sistema
                    actors.remove(actor);
                }
            }
            else {
                throw new NoSuchActorException();
            }

        }
        else {
            throw new NoSuchActorException();
        }

    }

    /**
     * Variabile creata per il test AllMessagesProcessedTest
     * Decommentarla per effettuare il test
     */
    public int numSendMessages;

    //------------------------------
}
