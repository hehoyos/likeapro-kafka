# Like a Pro Kafka

Bancolombia reactive programming course - **Task #3**

**Author:** Héctor Andrés Hoyos Ceballos

**C.C.:** 1039466317

**Description:** Project for the publishing of customers, events, recordings and statistics of the Like a Pro sports analysis platform from an Amazon SQS queue and Apache Kafka.

## Endpoints

---
* ### Customer
  * Send external customer to Kafka: `POST /customer/ext/`
  * Read customer SQS and send to Kafka: `GET /customer/ext/sqs/`
* ### Event
  * Send external event to Kafka: `POST /event/ext/`
  * Read event SQS and send to Kafka: `GET /event/ext/sqs/`
* ### Recording
  * Send external recording to Kafka: `POST /recording/ext/`
  * Read recording SQS and send to Kafka: `GET /recording/ext/sqs/`
* ### Statistics
  * Send external statistics to Kafka: `POST /statistics/ext/`
  * Read statistics SQS and send to Kafka: `GET /statistics/ext/sqs/`
