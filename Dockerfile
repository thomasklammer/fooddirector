# Stage that builds the application, a prerequisite for the running stage
FROM maven:3.8.1-openjdk-17-slim as build
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs

# Stop running as root at this point
RUN useradd -m myuser
WORKDIR /usr/src/app/
RUN chown myuser:myuser /usr/src/app/
USER myuser

# Copy pom.xml and prefetch dependencies so a repeated build can continue from the next step with existing dependencies
COPY --chown=myuser pom.xml ./
RUN mvn dependency:go-offline -Pproduction

# Copy all needed project files to a folder
COPY --chown=myuser:myuser src src
COPY --chown=myuser:myuser frontend frontend

# Build the production package, assuming that we validated the version before so no need for running tests again.
RUN mvn clean package -DskipTests -Pproduction -Dvaadin.offlineKey=eyJraWQiOiI1NDI3NjRlNzAwMDkwOGU2NWRjM2ZjMWRhYmY0ZTJjZDI4OTY2NzU4IiwidHlwIjoiSldUIiwiYWxnIjoiRVM1MTIifQ.eyJhbGxvd2VkUHJvZHVjdHMiOlsidmFhZGluLWNvbGxhYm9yYXRpb24tZW5naW5lIiwidmFhZGluLXRlc3RiZW5jaCIsInZhYWRpbi1kZXNpZ25lciIsInZhYWRpbi1jaGFydCIsInZhYWRpbi1ib2FyZCIsInZhYWRpbi1jb25maXJtLWRpYWxvZyIsInZhYWRpbi1jb29raWUtY29uc2VudCIsInZhYWRpbi1yaWNoLXRleHQtZWRpdG9yIiwidmFhZGluLWdyaWQtcHJvIiwidmFhZGluLW1hcCIsInZhYWRpbi1zcHJlYWRzaGVldC1mbG93IiwidmFhZGluLWNydWQiLCJ2YWFkaW4tY29waWxvdCJdLCJzdWIiOiIzZjQ2OWNmNi03ZWFhLTRhNTAtODBmZS03MmQxZjE4ODNiZjMiLCJ2ZXIiOjEsImlzcyI6IlZhYWRpbiIsIm5hbWUiOiJUaG9tYXMgS2xhbW1lciIsImFsbG93ZWRGZWF0dXJlcyI6WyJjZXJ0aWZpY2F0aW9ucyIsInNwcmVhZHNoZWV0IiwidGVzdGJlbmNoIiwiZGVzaWduZXIiLCJjaGFydHMiLCJib2FyZCIsImFwcHN0YXJ0ZXIiLCJ2aWRlb3RyYWluaW5nIiwicHJvLXByb2R1Y3RzLTIwMjIxMCJdLCJtYWNoaW5lX2lkIjoibWlkLWJ1aWxkc3J2LWNpcmNsZWNpIiwic3Vic2NyaXB0aW9uIjoiVmFhZGluIFBybyIsImJ1aWxkX3R5cGVzIjpbImRldmVsb3BtZW50IiwicHJvZHVjdGlvbiJdLCJleHAiOjE3NDU3OTg0MDAsImlhdCI6MTcxNDMxMTQ0MCwiYWNjb3VudCI6Ik1hbmFnZW1lbnQgQ2VudGVyIElubnNicnVjayJ9.AaHficBb19bmh7kFE8TP9Dn7DO4QidzzOAL-e9i2-tv4NKi2KZ2GhoUGZl3UT9Gb8tUTr_QHMN5wasJogBI5FfPJAPr-qm2hzYl9NmUourGlS_rNsInl76hXRRjk7b1QbNOp2BxwpkmCVnEHt1slZGiykMqTsI4IcaZV7FnJW5KD-njD

# Running stage: the part that is used for running the application
FROM maven:3.8.1-openjdk-17-slim
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar /usr/app/app.jar