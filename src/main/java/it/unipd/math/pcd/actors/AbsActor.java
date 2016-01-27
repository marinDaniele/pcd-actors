/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {


    /**
     * Container for PostedBy objects
     */
    private final MailBox<T, ActorRef<T> > mailBox;

    /**
     * variabile booleana che segnala se l'attore è attivo o no
     */
    private boolean active;

    /**
     * riferimento ad un oggeto di tipo PostedBy contenuto nella mailBox
     */
    private PostedBy<T,ActorRef<T>> posted;
    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * costruttore
     */
    public AbsActor(){
        mailBox = new MailBoxImpl<>();
        active = true;

        /**
         * Creo ed avvio il thread che durante tutta la vita dell'Actor
         * si occuperà di svuotare la mailBox e di invocare per ogni messaggio
         * il metodo receve(Message) dell'Actor
         */
        Thread reciveProcess = new Thread(new Runnable() {
            @Override
            public void run() {
                while ( active ) {
                    while ( mailBox.isEmpty() ) {
                        try {
                            mailBox.wait();
                        }
                        catch ( InterruptedException e ) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (mailBox) {
                        // recupero il messagio più vecchio presente nella mailBox
                        posted = mailBox.removeLast();
                        // imposto sender con il riferimento del sender del messaggio
                        sender = posted.getSender();
                    }
                    // invoco il metodo recive passandoli il messaggio
                    receive(posted.getMessage());

                }
                // L'attore non è più attivo quindi devo svuotare la mailBox
                while ( !mailBox.isEmpty() ) {
                    synchronized (mailBox) {
                        // recupero il messagio più vecchio presente nella mailBox
                        posted = mailBox.removeLast();
                        // imposto sender con il riferimento del sender del messaggio
                        sender = posted.getSender();
                    }
                    // invoco il metodo recive passandoli il messaggio
                    receive(posted.getMessage());
                }

            }
        });

        // avvio il thread reciveProcess
        reciveProcess.start();
    }

    /**
     * Restituisce lo stato dell'Actor, attivo o no
     * @return true se l'attore è attivo, false altrimenti
     */
    public boolean isActive() { return active; }

    /**
     * Disattiva l'attore su cui viene invocato
     */
    public void deactiveActor() { active = false; }

    /**
     * Metodo che avvia un thread per aggiungere un messaggio alla mailBox
     * Il messaggio verrà inserito alla mailBox solamente se l'attore è attivo
     * @param message oggetto di tipo derivato Message
     * @param sender oggetto di tipo derivato da ActorRef
     */
    public void addToMailBox(final T message, final ActorRef<T> sender) {

        // Creo un thread che aggiunge un messaggio alla mailBox
        // solamente se l'attore è ancora attivo
        Thread addMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                if( active ) {
                    synchronized (mailBox) {
                        PostedBy<T,ActorRef<T>> posted = new PostedBy<>(message, sender);
                        mailBox.add(posted);
                        mailBox.notifyAll();
                    }
                }
            }
        });

        // avvio il thread
        addMessage.start();

    }

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }
}
