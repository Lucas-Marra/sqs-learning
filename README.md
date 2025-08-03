# SQS study project

## Setup

### Docker

It will be necessary to have docker installed to run MySQL as our database and localstack as our AWS local environment.

To download it, you can follow the specific installation steps for your operational system on the [installation docs](https://docs.docker.com/engine/install/)

> [!TIP]
> **(Linux)**
> 
> You can run this command below to be able to run docker containers without needing sudo permissions
> ```shell
> sudo usermod -aG docker $(whoami)
> ```

### Java

Using version 21

### Database

This project uses MySQL (personal preference) inside a docker container

```shell
docker compose up -d mysql
```

### Localstack

Emulates our AWS environment.

[Installation docs](https://docs.localstack.cloud/aws/getting-started/installation/)

> [!NOTE]
> You need also to install `aws-cli` if you want access the "local aws" from the terminal
> 
> [Installation docs](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)

#### Set aws-cli test credentials:

run:
```shell
aws configure
```

And then set:

```AWS Access Key ID [None]:``` _Anything (test)_</br>
```AWS Secret Access Key [None]:``` _Anything (test)_</br>
```Default region name [None]:``` _Choose the best region for you (sa-east-1 for me)_</br>
```Default output format [None]``` _Nothing_

## Run project

### Start mysql and localstack services

First you need to run our mysql container:
```shell
docker compose up -d mysql
```

You also need to start the localstack environment (use a new terminal):
```shell
localstack start
```

Now you need to create the queue:
```shell
aws sqs create-queue --queue-name test-queue --endpoint-url=http://localhost:4566
```

You can check if the queue was created running the next script:
```shell
aws sqs list-queues --endpoint-url=http://localhost:4566
```

### Run the Spring Boot project

```shell
./mvnw spring-boot:run
```

To send a message to the queue you can use the command below:
```shell
curl --location 'http://localhost:8080/message-producer' \
--header 'Content-Type: Application/json' \
--data '{
    "content": "Message to be saved on database",
    "dateTime": "2025-07-27T20:28:38.906"
}'
```

The message should be sent to the application controller. The application produces the message and send it to sqs. After the message is in the queue, the application consumes and saves it on database.</br>
You can check it in the application logs or log into the database and do a query as instructed below

> [!TIP]
> To access database, run:
> ```shell
> docker exec -it mysql-container mysql -u root -p
> ```
> Choose the database:
> ```sql 
> use sqs-learning;
> ```
> Execute the query to show all saved messages:
> ```sql
> SELECT * FROM messages;
> ```