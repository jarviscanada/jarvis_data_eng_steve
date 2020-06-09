# Table of contents
* [Introduction](#introduction)
* [Architecture](#architecture)
* [Deployment Environments](#deployment-environments)
* [Jenkins](#jenkins)
* [Improvements](#improvements)

# Introduction
The [Trading platform microservice](../springboot) is now deployed on Amazon Web Services (AWS).
We utilized AWS service including EC2, RDS, Auto Scaling group and Elastic Beanstalk to maximize the 
scalability, elasticity, and availability of our application. Furthermore, we created a CI/CD pipeline 
using a provisioned Jenkins server to automate and fasten the deployment lifecycle.

# Architecture
- Draw a application cloud architecture diagram 
	- EC2, security groups
	- ALB, target group, auto-scaling
  - lable all ports, such as ALB, EC2, and RDS
	- example: https://www.notion.so/jarviscanada/Auto-Scaling-e303b20500614db6941601f6ed380cf3
- Describe how you made your application scalable and elastic by using cloud services (e.g. how does you app handle large traffic? what happens if your application server crashed)

# Deployment Environments
Describle how you use EB to create a DEV and a PROD env. What the purpose of these environments.
Draw a deployment environment diagram
	- EB
	- DEV and PROD env
	- example: https://www.notion.so/jarviscanada/Jenkins-CI-CD-Pipeline-681ce6e8078f4099860e5e7b22d52816

# Jenkins

## Jenkins Server
- Jenkins server configuration
	- Jenkins
	- NGINX rever proxy
	- label all ports
	- example: https://www.notion.so/jarviscanada/Jenkins-CI-CD-Pipeline-681ce6e8078f4099860e5e7b22d52816

## Jenkins CI/CD pipeline
Describe how you create a CI/CD pipeline using Jenkins.
Describe pipeline steps using a diagram

# Improvements
List at least three improvements 

