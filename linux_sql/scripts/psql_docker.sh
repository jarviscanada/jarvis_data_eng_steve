#! /bin/bash

op=$1
pwd=$2

if [ "$#" -gt 2 ]; then
    echo "warning: only 2 args are needed, the rest will be ignored"
fi

if [ "$op" = "stop" ]; then
    docker stop jrvs-psql
elif [ "$op" = "start" ]; then
    if [ -z "$pwd" ]; then
        >&2 echo "error: please provide a postgres password"
        exit 1
    fi
    # systemctl status docker || systemctl start docker
    if [ "$(systemctl is-active docker)" = "unknown" ]; then
        echo "starting docker daemon..."
        systemctl start docker
    fi

    if [ $(docker ps -f name=jrvs-psql | wc -l) -eq 2 ]; then
        echo "jrvs-psql container is running already"
    else
        # docker inspect pgdata || docker volume create pgdata
        if [ -z "$(docker inspect --format '{{ .Mountpoint }}' pgdata 2> /dev/null)" ]; then
            echo "creating local volumn..."
            docker volume create pgdata
        fi
        # check if `jrvs-psql` container is created
        if [ $(docker ps -a -f name=jrvs-psql | wc -l) -ne 2 ]; then
            echo "starting container..." 
            docker run \
            --name jrvs-psql \
            -e POSTGRES_PASSWORD=$pwd \
            -v pgdata:/var/lib/postgresql/data \
            -p 5432:5432 \
            -d postgres
        else
            docker container start jrvs-psql
        fi
    fi
else
    >&2 echo "error: '${op}' is not a valid operation"
    exit 127
fi

exit 0
