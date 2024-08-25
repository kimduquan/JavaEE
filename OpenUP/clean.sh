. ./env.sh
rm -R -d $PLUTO_HOME/webapps/*.war
rm -R -d $PLUTO_HOME/webapps/EPF-security-portlet
rm -R -d $PLUTO_HOME/webapps/EPF-persistence-portlet
rm -R -d $PLUTO_HOME/webapps/EPF-messaging-portlet
rm -R -d $PLUTO_HOME/webapps/EPF-file-portlet
rm -rf /tmp/kafka-logs
rm -rf /tmp/zookeeper
rm -R -d ObjectStore