#! /bin/bash

systemctl status docker || systemctl start docker

if [ $(docker ps -f name=jrvs-psql | wc -l) -eq 2 ]; then
    echo 'jrvs-psql container is running'
    exit 0
fi

exit 0
