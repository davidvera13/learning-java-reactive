// https://stackoverflow.com/questions/60522471/docker-compose-mongodb-docker-entrypoint-initdb-d-is-not-working
print('Start db creation #################################################################');
db.createUser({
    user: 'root',
    pwd: 'root',
    roles: [
        {
            role: 'readWrite',
            db: 'rsocket',
        },
    ],
})

db = new Mongo().getDB("rsocket");
db.createCollection('users', { capped: false });
db.createCollection('stocks', { capped: false });

print('END db creation #################################################################');
