C:
cd C:\Users\lcaltab\Documents\NetBeansProjects\Speculate\Speculate\dist
start java -cp Speculate.jar Speculate.SpeculateServer
timeout /t 10

FOR /L %%A IN (1,1,4) DO (
  start java -cp Speculate.jar Speculate.SpeculateClient
  ECHO %%A
) 

