FROM maven:3.5.2-jdk-8
# debian based

ENV HOME="/home/AutomationFramework"
ENV JAVA_OPTS=""
ENV ENVIRONMENT=""
ENV TAGS="@pmi"
ENV DEVICE="htmlunit"
ENV DEFAULT_WAIT="5"
ENV S3_ACCESS=""
ENV S3_SECRET=""
ENV S3_BUCKET=""
ENV EMAIL_USERNAME=""
ENV EMAIL_PASSWORD=""

# Create our working directory
RUN mkdir -p ${HOME}
WORKDIR ${HOME}

# Copy / install the basics - do this here because COPY does not honor USER
COPY src src
COPY pom.xml .
COPY src/main/resources/docker/default/docker-entrypoint.sh /usr/local/bin

# try setting the timezone
ARG TZ=America/Los_Angeles
ENV TZ=$TZ
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME ["/home/AutomationFramework/test-output"]

CMD ["docker-entrypoint.sh"]
