  
FROM jboss/wildfly:latest

ADD scripts /opt/jboss/wildfly/scripts/
ADD files /opt/jboss/wildfly/files/
ADD deployment /opt/jboss/wildfly/standalone/deployments

RUN /opt/jboss/wildfly/scripts/execute.sh