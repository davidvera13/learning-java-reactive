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

| Java     | proto               |
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