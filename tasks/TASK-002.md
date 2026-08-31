# TASK-002：本地 MySQL、Redis 和环境配置

## 目标

建立本地 MySQL 8、Redis 和 Spring Boot 连接配置，为后续业务开发提供可复现的基础设施配置模板。

## 背景

项目架构规划使用 MySQL 保存正式业务数据、Redis 保存缓存和临时状态。本任务只建立配置，不实现数据库业务功能。

## 涉及文件

```text
docker-compose.yml
.env.example
.env (本地未跟踪文件，不提交)
backend/src/main/resources/application.yml
backend/pom.xml
backend/src/test/java/com/projectlearn/app/InfrastructureConnectionTests.java
.gitignore
```

## 实现内容

- 创建只包含 MySQL 8 和 Redis 的 `docker-compose.yml`。
- 配置端口映射和持久化数据卷。
- 创建不包含真实秘密的 `.env.example`。
- 配置 Spring Boot 使用环境变量连接 MySQL 和 Redis。
- 补充 Spring JDBC、MySQL Connector/J 和 Spring Data Redis 最小依赖。
- 增加基础设施集成测试，执行 MySQL `SELECT 1` 和 Redis `PING`。
- 为本地环境增加 `.env`、`.env.*` 忽略规则，同时保留 `.env.example`。

## 不做什么

- 不实现数据库表、ORM、认证、JWT、业务 API 或 AI。
- 不加入其他 Docker 服务或 healthcheck 要求之外的基础设施。

## 验收标准

- Compose 文件只包含 MySQL 8 和 Redis。
- MySQL 和 Redis 均有端口映射和数据卷。
- 环境变量模板不包含真实密码或 API Key。
- Spring Boot 默认以 `localhost` 连接本机暴露的 MySQL 和 Redis 端口。
- `.env` 被 Git 忽略，`.env.example` 保留。

## 测试方法

- 静态检查 Compose 服务、镜像、端口、数据卷和变量引用。
- 静态检查 `.gitignore` 规则。
- Docker Compose 服务由开发者在本机启动，使用 MySQL 宿主端口 3307、Redis 宿主端口 6379。
- 使用显式环境变量启动 Spring Boot，避免误以为 Spring Boot 会自动读取根目录 `.env`。
- 运行后端测试和打包。
- 运行 `InfrastructureConnectionTests`，实际执行 MySQL `SELECT 1` 和 Redis `PING`。

## 当前状态

- [x] 配置完成
- [x] 测试完成
- [ ] Git Commit
- [ ] Git Push

## 实际实现记录

已创建 `docker-compose.yml`、`.env.example` 和 `backend/src/main/resources/application.yml`，并在现有 `.gitignore` 中追加环境文件忽略规则。由于实际连接验证发现后端原先缺少数据库和 Redis 依赖，已在 `backend/pom.xml` 中补充 `spring-boot-starter-jdbc`、`mysql-connector-j`（runtime）和 `spring-boot-starter-data-redis`。新增 `InfrastructureConnectionTests` 作为仅基础设施连接测试，不包含业务逻辑。

本地 `.env` 使用 `MYSQL_PORT=3307`，Spring Boot 通过环境变量连接 `localhost:3307`；Redis 连接 `localhost:6379`。根目录 `.env` 不由 Spring Boot 自动加载，启动测试时显式注入了变量。

## 遇到的问题

最初使用默认宿主端口 3306 启动 Compose 时发生端口占用，确认是本机 MySQL 服务占用；改为 3307 后，开发者已验证 Compose 服务可用。当前受限执行环境无法直接读取 Docker CLI 状态，但 Spring Boot 集成测试已通过，证明 MySQL `SELECT 1` 和 Redis `PING` 均成功。

## 最终结果

配置、最小依赖和实际连接测试已完成。执行全量 `mvn test` 通过（2 个测试、0 失败、0 错误），其中 `InfrastructureConnectionTests` 实际执行 MySQL `SELECT 1` 和 Redis `PING`；日志显示 Hikari 已成功建立 MySQL 连接。Spring Boot 已使用相同环境变量成功启动，`/health` 返回 `{"status":"UP"}`。TASK-002 尚未最终 commit/push，以当前 Git 状态为准。
