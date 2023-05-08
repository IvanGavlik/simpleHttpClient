package com.ivangavlik.http.poc;



public class Client {
    Adapter adapter = new Adapter();
    public void doTask() {
        adapter.callService();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.doTask();
    }
}

/**
 *  provide a transparent interface for clients to communicate with external servers.
 *  Adapter hides the system dependencies such as communication facilities from the client
 */
class Adapter {

    ExternalServer externalServer = new ExternalServer();
    Microkernel microkernel = new Microkernel();
    public void callService() {}

    public void createRequest() {}
}

/**
 * The external server provides a more complex functionality;
 * they are built on top of the core services provided by the microkernel.
 * Different external servers may be needed in the system in order to provide
 * the functionality for specific application domains.
 */
class ExternalServer {
    public void receiveRequest() {}

    public void dispatchRequest() {}

    public void executeRequest() {}
}

/**
 *  Used to encapsulate core services of the system
 *
 *  Also maintains the system resources and allows other components to
 *  interact with each other as well as to access the core functionality
 */
class Microkernel {
    public void executeMechanism() {}

    public void initCommunication() {}

    public void findReceiver() {}

    public void createHandle() {}

    public void sendMsg() {}

    public void callInternalServer()  {}
}


/**
 * Internal servers extend the functionalities of the microkernel.
 */
class InternalServer {
    public void executeService() {}

    public void receriveRequest() {}
}
