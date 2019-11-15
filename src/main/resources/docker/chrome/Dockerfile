FROM maven:3.5.2-jdk-8
# debian based

ENV HOME="/home/PMTAutomationFramework"
ENV JAVA_OPTS=""
ENV ENVIRONMENT=""
ENV TAGS="@pmt"
ENV DEVICE="chrome"
ENV DEFAULT_WAIT="5"
ENV S3_ACCESS=""
ENV S3_SECRET=""
ENV S3_BUCKET=""
ENV EMAIL_USERNAME=""
ENV EMAIL_PASSWORD=""

#Install chrome
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update -qqy \
	&& apt-get -qqy install google-chrome-stable --allow-unauthenticated \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox --disable-dev-shm-usage/g' /opt/google/chrome/google-chrome

# Create our working directory
RUN mkdir -p ${HOME}
WORKDIR ${HOME}

# Copy / install the basics - do this here because COPY does not honor USER
COPY src src
COPY pom.xml .
COPY src/main/resources/docker/chrome/docker-entrypoint.sh /usr/local/bin

# try setting the timezone
ARG TZ=America/Los_Angeles
ENV TZ=$TZ
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME ["/home/PMTAutomationFramework/test-output"]

CMD ["docker-entrypoint.sh"]
