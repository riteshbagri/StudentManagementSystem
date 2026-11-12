@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF)
@REM Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET ___MVNW_UGLY_PROXY=
@IF NOT "%MVNW_REPOURL%" == "" SET ___MVNW_UGLY_PROXY=-Dmaven.repo.local="%USERPROFILE%\.m2\repository"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

@FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%~dp0.mvn\wrapper\maven-wrapper.properties") DO (
    @IF "%%A"=="distributionUrl" SET DISTRIBUTION_URL=%%B
    @IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
)

@SET MAVEN_PROJECTBASEDIR=%~dp0
IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%

@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET DOWNLOAD_JAR_URL=%WRAPPER_URL%
@IF DEFINED MVNW_REPOURL SET DOWNLOAD_JAR_URL=%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar

@IF EXIST %WRAPPER_JAR% (
    @REM Wrapper JAR already downloaded
) ELSE (
    @ECHO Downloading Maven Wrapper...
    @ECHO Distribution URL: %DISTRIBUTION_URL%
    @powershell -Command "& {Invoke-WebRequest -Uri '%DOWNLOAD_JAR_URL%' -OutFile '%WRAPPER_JAR%'}" 2>NUL
    @IF ERRORLEVEL 1 (
        @ECHO Could not download Maven Wrapper. Please install Maven manually.
        @EXIT /B 1
    )
)

@SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
@IF NOT EXIST "%JAVA_EXE%" (
    @FOR /F "tokens=*" %%F IN ('where java 2^>NUL') DO @SET JAVA_EXE=%%F
)

"%JAVA_EXE%" -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %*

