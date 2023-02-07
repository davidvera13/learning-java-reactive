## grpc channels
![2023-02-07_07h53_39.png](img%2F2023-02-07_07h53_39.png)
![2023-02-07_07h54_13.png](img%2F2023-02-07_07h54_13.png)
## lazy connection

We can connect to server with the following code

````
ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 3615)
    .usePlaintext()
    .build();
````

An error is thrown once a requst is made: by default connection is lazy

`io.grpc.StatusRuntimeException: UNAVAILABLE: io exception`

## load balancing

We use single responsibility design for splitting application functionalities.
![2023-02-07_08h14_03.png](img%2F2023-02-07_08h14_03.png)
Here we can distribute the load of order service on different instances of payment service
How will load balancing will work ? 

Server side and client side load balancing are available

### Service side load balancing through proxy

![2023-02-07_08h19_48.png](img%2F2023-02-07_08h19_48.png)

Using nginx with docker: here is the docker compose file 
````
version: "3"
services:
  nginx:
    image: nginx:latest
    volumes:
      - ./conf:/etc/nginx/conf.d
    ports:
      - 8585:8585

````

and its configuration 

````
upstream bankservers {
    server 172.30.0.1:5678;
    server 172.30.0.1:6789;
}

server {
    # will use explicitelly http2
    listen 8585 http2;
    location / {
        grpc_pass grpc://bankservers;
    }
}

````

We can use load balancing for blocking and non blocking calls.

### Client side load balancing without proxy

Client side load balancing require to know IP addresses. There won't be any proxy service. 
IP may be known using registry service such as Zuul for example or Consul. 


![2023-02-07_18h33_10.png](img%2F2023-02-07_18h33_10.png)

We use sub channels. Each sub channel concern one IP address. 

![2023-02-07_18h36_46.png](img%2F2023-02-07_18h36_46.png)

2 strategies: 
 - pick first strategy
 - round robin strategy (not the default strategy)





