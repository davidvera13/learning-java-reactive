## setup project
this setup requires node installed. 
First command using node is for setting up an empty project: 
````
λ npm init -y
````

The answer is the following:
````
Wrote to node-client\package.json:

{
"name": "client",
"version": "1.0.0",
"description": "",
"main": "index.js",
"scripts": {
"test": "echo \"Error: no test specified\" && exit 1"
},
"keywords": [],
"author": "",
"license": "ISC"
}
````

A second command line is required to import grpc dependencies: 

````
λ npm install @grpc/proto-loader grpc
````