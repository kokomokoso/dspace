--
-- Copyright 2010-2017 Boxfuse GmbH
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--
-----------------
-- This is the PostgreSQL upgrade script from Flyway v4.2.0, copied/borrowed from:
-- https://github.com/flyway/flyway/blob/flyway-4.2.0/flyway-core/src/main/resources/org/flywaydb/core/internal/dbsupport/postgresql/upgradeMetaDataTable.sql
--
-- The variables in this script are replaced in FlywayUpgradeUtils.upgradeFlywayTable()
------------------

DROP INDEX "${schema}"."${table}_vr_idx";
DROP INDEX "${schema}"."${table}_ir_idx";
ALTER TABLE "${schema}"."${table}" DROP COLUMN "version_rank";
ALTER TABLE "${schema}"."${table}" DROP CONSTRAINT "${table}_pk";
ALTER TABLE "${schema}"."${table}" ALTER COLUMN "version" DROP NOT NULL;
ALTER TABLE "${schema}"."${table}" ADD CONSTRAINT "${table}_pk" PRIMARY KEY ("installed_rank");
UPDATE "${schema}"."${table}" SET "type"='BASELINE' WHERE "type"='INIT';
