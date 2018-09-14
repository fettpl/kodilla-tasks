#!/usr/bin/env bash

source ./runcrud.sh

if bash runcrud.sh; then
    start_tomcat
    open "http://localhost:8080/crud/v1/task/getTasks"
else
    fail
fi