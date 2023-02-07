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

Using nginx with docker
````


````

