@echo off
aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/suporteTecnico_localstack/suporte" --value "Support Parameter Store" --type String