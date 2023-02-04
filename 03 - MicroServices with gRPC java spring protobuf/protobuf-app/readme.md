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