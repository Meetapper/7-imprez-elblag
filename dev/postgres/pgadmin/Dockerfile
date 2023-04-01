FROM dpage/pgadmin4
COPY --chown=5050:5050 servers.json /pgadmin_settings/servers.json
COPY --chown=5050:5050 pgpassfile /pgadmin_settings/pgpassfile
RUN chmod 600 /pgadmin_settings/pgpassfile
ENV PGPASSFILE=/pgadmin_settings/pgpassfile
ENV PGADMIN_SERVER_JSON_FILE=/pgadmin_settings/servers.json
