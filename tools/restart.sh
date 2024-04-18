ssh seproj@123.249.103.199 'bash -s' << 'ENDSSH'
echo 'Restarting Server'
ps -ef | grep server.jar | awk '{print $2}' | xargs kill -9
nohup java -jar server.jar > server.log 2>server-error.log &
exit
ENDSSH