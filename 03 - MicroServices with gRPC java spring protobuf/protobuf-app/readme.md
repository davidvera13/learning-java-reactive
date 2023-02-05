## protobuf

A protobuf class: has the following syntax 
```
syntax = "proto3";

message Person {
string name = 1;
int32 age = 2;
}
```

Note the number after field indicates field order
this defines the person class

executes 
```
maven compile
```

in target/protoc-pu
in target/generated-sources/protobuf/java we have generated classes

we can use different options in class: 

```
option java_multiple_files=true;
```

it allows to split in different files the generated class

```
option java_package="com.proto.models";
```

this option adds package to the class

# Generating class for javascript

Using a terminal, go to the folder proto and type the following command line
```
..\..\..\target\protoc-plugins\protoc-3.22.0-rc-1-windows-x86_64.exe --js_out=./ *.proto
```

protoc-gen-js' is not recognized as an internal or external command
To solve the issue: 

```
A simpler workaround(other than downgrading) is to download the executable from https://github.com/protocolbuffers/protobuf-javascript/releases and add it to your PATH. Then it will work as expected
commande line is 
For check: 
λ protoc --version
libprotoc 3.20.1

For generation:
λ protoc --js_out=./ *.proto
```

### primitive types

| Java                | proto              |
|---------------------|--------------------|
| int                 | int32              |
| long                | int64              |
| float               | float              |   
| double              | double             |
| boolean             | bool               |
| string              | string             |
| byte[]              | bytes              |



### Collections & map

| Java              | proto    |
|-------------------|----------|
| Collection / list | repeated |
| map               | map      |


### Default values

| Proto    | default values      |
|----------|---------------------|
| int32    | 0                   |
| int64    | 0                   |
| float    | 0                   |   
| double   | 0                   |
| bool     | false               |
| string   | empty string        |
| enum     | first value         |
| repeated | empty list          |   
| map      | wrapper / empty map |   


## Creating package

We can use mvn clean compile and the classes are generated in target folder
or use 

```
λ protoc --java_out=./ *.proto
```
The classes are generated in the same folder 


## Creating interfaces and implementation 

To create an interface, we need to create for example 2 messages objects and the interface will be declared this way
Here oneof means that the credentials interface is implemented by both messages and can be used by one at the time.
```
message Credentials {
  oneof authMode {
    EmailCredentials emailCredentials=1;
    PhoneOTP phoneOTP=2;
  }
}
```

## Wrapper types

A proto file must be imported to use wrappers
```
import "google/protobuf/wrappers.proto";
```

| Proto  | Wrapper       |
|--------|---------------|
| int32  | Int32Value    |
| int64  | Int64Value    |
| float  | FloatValue    |   
| double | DoubleValue   |
| bool   | BoolValue     |
| string | StringValue   |
| bytes  | BytesValue    |
|        | UInt32Value   |   
|        | UInt64Value   |   


Example:

````
message Client {
// person name
string name = 1;
// person age
//int32 age = 2;
google.protobuf.Int32Value age = 2;

// composition
commons.Address address = 3;
repeated commons.Car cars = 4;
}
````
int32 was replaced with google.protobuf.Int32Value
Note: if we use wrapper we must also use the constructor: 

The following code should bring a compile error:

````
        Client client = Client.newBuilder()
                .setName("Alan")
                .setAge(25)
                .setAddress(Address.newBuilder()
                        .setPostBox(1)
                        .setStreet("Sesame Street")
                        .setCity("London")
                        .build())
                .addCars(Car.newBuilder()
                        .setMake("Ford")
                        .setModel("Mustang")
                        .setYear(1968)
                        .build())
                .addCars(Car.newBuilder()
                        .setMake("Ashton Martin")
                        .setModel("DB5")
                        .setYear(1963)
                        .build())
                .build();

````

Correct code should be the following 

````
Client client = Client.newBuilder()
        .setName("Alan")
        .setAge(Int32Value.newBuilder()
                .setValue(25)
                .build())
        .setAddress(Address.newBuilder()
                .setPostBox(1)
                .setStreet("Sesame Street")
                .setCity("London")
                .build())
        .addCars(Car.newBuilder()
                .setMake("Ford")
                .setModel("Mustang")
                .setYear(1968)
                .build())
        .addCars(Car.newBuilder()
                .setMake("Ashton Martin")
                .setModel("DB5")
                .setYear(1963)
                .build())
        .build();
````

Wrappers also bring use the has boolean validator.

````
System.out.println(client.hasAge());
````

