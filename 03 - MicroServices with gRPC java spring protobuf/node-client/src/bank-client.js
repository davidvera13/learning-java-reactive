const grpc = require("grpc");
const protoloader = require("@grpc/proto-loader");

const packageDef = protoloader.loadSync('./proto/bank-service.proto');
const protoDescriptor = grpc.loadPackageDefinition(packageDef);

const client = new protoDescriptor.BankService("localhost:6789", grpc.credentials.createInsecure());

client.getBalance({ accountNumber: 4 }, (err, balance) => {
    if(err)
        console.log("Ooops something went wrong: " + err.toLocaleString())
    else
        console.log("Received balance: " + balance.amount);
})