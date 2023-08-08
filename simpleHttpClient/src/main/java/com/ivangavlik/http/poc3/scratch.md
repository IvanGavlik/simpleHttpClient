# Steps for Microkernel architecture

Todo how plugins are loaded, and can be customized, I need to investigate more on plug-in play architecture

## 1. 
Core functionality that are needed to be implemented in service

## 2. 
Categorize them as external or internal

## 3. 
Microkernel provides mechanism not policies. Each policy (external service)
Policy is implemented using MK interfaces 

Define interfaces that MK has 
* create and terminate processes and threads
* reading and writing (resources access)
* manage communication

## 4.
Define communication strategies for request transmission and retrieval
* asynchronous
* synchronous

Define relations between communications entities
* 1:1
* M:1
* 1:M

Check forward receiver and client dispatch receiver

## 5.
Microkernel as layered  
Separate system specific from system independent parts
Is MK a separate process or module

## 6.
Adapter is interface to client giving service unsing external services as proxy 
* avaliable for all clients or one client is associated with one adapter 
