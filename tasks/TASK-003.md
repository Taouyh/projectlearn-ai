# TASK-003：后端通用响应、异常和配置

## 目标

建立后端统一响应、异常处理和基础配置约定。

## 背景

后续业务模块需要统一的 API 成功响应、错误响应和异常处理方式，避免各模块自行定义格式。

## 涉及文件

```text
backend/src/main/java/com/projectlearn/common/
backend/src/test/java/com/projectlearn/common/
```

## 实现内容

- 定义统一成功响应格式。
- 定义统一错误响应格式和错误码。
- 添加全局异常处理。
- 添加必要的参数校验和配置约定。
- 确保错误信息不泄露密钥或内部敏感内容。

## 不做什么

- 不实现认证、JWT、数据库模型、业务模块或 AI Provider。
- 不改变既定模块化单体架构。

## 验收标准

- 成功、参数错误、业务错误和未知错误的响应格式统一。
- 错误响应不泄露内部堆栈和敏感信息。
- 通用基础代码具有服务层或接口层测试。

## 测试方法

- Controller 异常测试。
- 参数校验测试。
- 错误响应结构测试。

## 当前状态

- [x] 配置完成
- [x] 测试完成
- [ ] Git Commit
- [ ] Git Push

## 实际实现记录

已实现统一 `ApiResponse`、`ErrorCode`、`BusinessException` 和 `GlobalExceptionHandler`，并将 `/health` 响应统一为 `ApiResponse`。补充 Spring Validation 依赖和通用异常处理测试，覆盖业务错误、参数校验错误和未知错误脱敏。

## 遇到的问题

TASK-002 的基础设施连接测试出现 MySQL 用户密码与已有 Docker 数据卷不一致的问题；Redis 与 ProjectLearn AI 共用 `localhost:6379`，应用使用 Redis DB 0。该问题不影响 TASK-003 的纯单元测试。全量测试需在仅处理 MySQL 数据卷凭据后重新执行，Redis 数据卷不得重置。

## 最终结果

TASK-003 的通用响应和异常处理实现已完成。`GlobalExceptionHandlerTest` 通过（3 个测试、0 失败、0 错误）；在修复 MySQL 数据卷凭据、保持 Redis 容器和数据卷不变后，后端全量测试 `mvn -f backend\pom.xml test` 已通过。当前 TASK-003 相关改动已完成，尚未 commit/push。
