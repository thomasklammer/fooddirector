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
RUN mvn clean package -DskipTests -Pproduction -Dvaadin.offlineKey=eyJraWQiOiI1NDI3NjRlNzAwMDkwOGU2NWRjM2ZjMWRhYmY0ZTJjZDI4OTY2NzU4IiwidHlwIjoiSldUIiwiYWxnIjoiRVM1MTIifQ.eyJhbGxvd2VkUHJvZHVjdHMiOlsidmFhZGluLWNvbGxhYm9yYXRpb24tZW5naW5lIiwidmFhZGluLXRlc3RiZW5jaCIsInZhYWRpbi1kZXNpZ25lciIsInZhYWRpbi1jaGFydCIsInZhYWRpbi1ib2FyZCIsInZhYWRpbi1jb25maXJtLWRpYWxvZyIsInZhYWRpbi1jb29raWUtY29uc2VudCIsInZhYWRpbi1yaWNoLXRleHQtZWRpdG9yIiwidmFhZGluLWdyaWQtcHJvIiwidmFhZGluLW1hcCIsInZhYWRpbi1zcHJlYWRzaGVldC1mbG93IiwidmFhZGluLWNydWQiLCJ2YWFkaW4tY29waWxvdCJdLCJzdWIiOiIzZjQ2OWNmNi03ZWFhLTRhNTAtODBmZS03MmQxZjE4ODNiZjMiLCJ2ZXIiOjEsImlzcyI6IlZhYWRpbiIsIm5hbWUiOiJUaG9tYXMgS2xhbW1lciIsImFsbG93ZWRGZWF0dXJlcyI6WyJjZXJ0aWZpY2F0aW9ucyIsInNwcmVhZHNoZWV0IiwidGVzdGJlbmNoIiwiZGVzaWduZXIiLCJjaGFydHMiLCJib2FyZCIsImFwcHN0YXJ0ZXIiLCJ2aWRlb3RyYWluaW5nIiwicHJvLXByb2R1Y3RzLTIwMjIxMCJdLCJtYWNoaW5lX2lkIjpudWxsLCJzdWJzY3JpcHRpb24iOiJWYWFkaW4gUHJvIiwiYnVpbGRfdHlwZXMiOlsicHJvZHVjdGlvbiJdLCJleHAiOjE3NDcyNjcyMDAsImlhdCI6MTcxNTc4MjczNSwiYWNjb3VudCI6Ik1hbmFnZW1lbnQgQ2VudGVyIElubnNicnVjayJ9.ATL9irrCdbnUZsQ7howanKTcylPFOKzc_qf6BmEBI---i0S8zTv83jmfsoWp7_gwm8T8Nr7VTYQjarYBlQNccJZiASNi0lUjUBEZNjRSRKQCopn-LUrPsGhqcoK5xMDCEqgfhfpN22KfJZeAy2JzJZixxvdhzDOhEp3PoeUp11mwKWvW

# Running stage: the part that is used for running the application
FROM maven:3.8.1-openjdk-17-slim
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar /usr/app/app.jar
