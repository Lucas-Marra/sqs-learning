# SQS study project

The main purpose of this project is to learn how to integrate a Spring Boot application with an SQS service. I also chose to explore Docker by using it to deploy all my services (Localstack, database, and Spring Boot application)

## Setup

### Docker

You will need to have Docker installed to run MySQL as our database and localstack as our AWS local environment.

To download it, you can follow the specific installation steps for your operating system in the [installation docs](https://docs.docker.com/engine/install/)

> [!TIP]
> **(Linux)**
> 
> Run the following command to allow running Docker containers without needing sudo permissions:
> ```shell
> sudo usermod -aG docker $(whoami)
> ```

### Java

This project uses Java version 21

### Database

This project uses MySQL (personal preference) inside a Docker container

```shell
docker compose up -d mysql
```

### Localstack

Emulates the AWS environment locally.

I'm using it in a Docker container, but you can follow the steps below to install it on your machine if you prefer.

> [!NOTE]
> **Localstack Installation and Configuration**
> 
> [Localstack Installation docs](https://docs.localstack.cloud/aws/getting-started/installation/)
> 
> You'll also need to install `aws-cli` to access the "local AWS" from the terminal:
> 
> [aws-cli Installation docs](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
>
> #### Set AWS CLI test credentials:
>
> Run:
> ```shell
> aws configure
> ```
> 
> Then, set the following:
> 
> ```AWS Access Key ID [None]:``` _Anything (test)_</br>
> ```AWS Secret Access Key [None]:``` _Anything (test)_</br>
> ```Default region name [None]:``` _Choose the best region for you (sa-east-1 for me)_</br>
> ```Default output format [None]``` _Nothing_

## Run project

### Start mysql and localstack services

```shell
docker compose up -d mysql localstack
```

> [!NOTE]
> **If you want to create sqs queue manually, follow these steps:**
> 
> To create a queue with the name `test-queue`, run:
> ```shell
> aws sqs create-queue --queue-name test-queue --endpoint-url=http://localhost:4566
> ```
> 
> You can check if the queue was created by running:
> ```shell
> aws sqs list-queues --endpoint-url=http://localhost:4566
> ```

### Run the Spring Boot project

```shell
./mvnw spring-boot:run
```

To send a message to the queue, use the following command: 
```shell
curl --location 'http://localhost:8080/message-producer' \
--header 'Content-Type: Application/json' \
--data '{
    "content": "Message to be saved on database",
    "dateTime": "2025-07-27T20:28:38.906"
}'
```

The message will be sent to the application controller. The application will then produce the message and send it to the SQS queue. After the message is in the queue, the application will consume and save it in the database.</br>
You can check the application logs or log into the database and run the following query:

> [!TIP]
> To access database, run:
> ```shell
> docker exec -it mysql-container mysql -u root
> ```
> Choose the database:
> ```sql 
> use sqs-learning;
> ```
> 
> Execute the query to show all saved messages:
> ```sql
> SELECT * FROM message;
> ```

### Running the application on a docker container as well

First, build the application artifact:
```shell
./mvnw clean package
```

Then, start the Docker container:
```shell
docker compose up --attach app --no-log-prefix --build    
```