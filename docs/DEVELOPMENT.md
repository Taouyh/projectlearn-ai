# ProjectLearn AI 开发规范

## 适用范围

本文档约束仓库中的前端、后端、文档、Prompt、示例资料和配置变更。它服务于个人开发和开源协作，不能替代具体模块的设计评审。

## 项目目录规范

```text
projectlearn-ai/
├── frontend/             Vue 3 前端
├── backend/              Spring Boot 后端
├── docs/                 产品、架构、路线图和开发文档
├── examples/             HMDP 等可公开示例
├── prompts/              可版本化的 Prompt 模板（如单独维护）
├── scripts/              开发和数据处理脚本
├── docker-compose.yml
├── .env.example
├── README.md
└── LICENSE
```

后端按业务模块组织：`common`、`user`、`project`、`material`、`knowledge`、`learning`、`task`、`submission`、`assessment`、`ai`。每个模块优先采用 `controller/service/mapper/entity/dto/vo` 结构。

## Git 规范

- `main` 始终保持可构建、可运行或明确标注不可运行原因。
- 一个提交只解决一个清晰问题，避免混入无关格式化。
- 提交前检查敏感信息、构建结果和测试结果。
- 不提交生成目录、IDE 配置、日志、真实环境文件、模型密钥和用户资料。
- 合并前更新受影响的文档和变更说明。

## Commit 规范

采用 Conventional Commits：

```text
feat: add project material upload
fix: handle empty assessment response
docs: update MVP boundary
refactor: extract retrieval service
test: add knowledge map service tests
chore: update dependency version
```

格式为 `<type>: <imperative summary>`。标题使用英文、简短且说明结果；正文在需要时说明原因、影响和验证方式。

## 分支规范

- `main`：稳定主线。
- `feature/<short-name>`：新功能。
- `fix/<short-name>`：缺陷修复。
- `docs/<short-name>`：文档修改。
- `refactor/<short-name>`：不改变外部行为的重构。

分支应短生命周期、保持单一目标，合并后删除。个人开发也建议通过 Pull Request 检查变更，而不是直接向 `main` 推送大改动。

## 代码规范

### 通用

- 优先可读性和简单实现，不为未来假设预留复杂抽象。
- 公共接口、关键业务规则和非显然逻辑必须有注释或文档。
- 错误信息应能帮助用户定位问题，但不得泄露密钥或内部敏感内容。
- API 请求/响应使用明确 DTO，不直接暴露数据库实体。
- 配置通过环境变量或配置文件注入，禁止硬编码凭据。

### 前端

- 使用 TypeScript，避免无必要的 `any`。
- 页面负责组合，业务请求集中在 `api/`，跨页面状态使用 Pinia。
- Mermaid、AI 对话、知识地图等复杂区域拆成可测试组件。
- 统一处理加载、空状态、错误状态和权限失败。

### 后端

- 遵守 `Controller → Service → Mapper` 依赖方向。
- 业务模块依赖 `LlmProvider` 接口，不直接调用厂商 SDK。
- 对 AI 输出做结构校验、超时、重试和可理解的失败处理。
- 重要业务状态和用户输出写入 MySQL；Redis 仅用于缓存、临时状态、会话和限流。
- 资料片段必须保留原始文件路径等来源信息。

## 测试规范

- 新增业务规则至少包含服务层单元测试。
- 资料解析、Provider、知识地图生成和评估流程优先覆盖边界输入与失败路径。
- 数据库交互使用集成测试验证映射和事务行为。
- MVP 每个阶段都应保留一条可重复的端到端验收流程。
- AI 调用测试默认使用 Mock Provider；不在 CI 中依赖个人 API Key。
- 测试数据必须是脱敏、可公开或专门生成的数据。

## AI Coding Agent 使用规范

- Agent 开始工作前必须阅读相关产品、架构和开发文档。
- Agent 应先说明将修改的范围、假设和验证方式。
- Agent 应优先做小步、可回滚的变更，并保持现有目录和模块边界。
- 涉及数据库结构、公共 API、Prompt 输出格式或 AI Provider 的改动，必须说明兼容性影响。
- Agent 完成后必须报告修改文件、测试/构建结果、未验证事项和潜在风险。
- Agent 不应将未来规划当作当前需求实现。

特别规定：

> AI Coding Agent 不允许未经确认进行大规模重构、删除文件、修改核心架构或引入新的基础设施。

以下操作必须先确认：

- 删除或批量移动现有文件。
- 修改数据库核心表、公共 API 或认证机制。
- 将模块化单体拆成微服务。
- 引入向量数据库、消息队列、Kubernetes 或新的外部托管服务。
- 大范围更换前端 UI、ORM、数据库或 AI Provider 方案。

## 禁止事项

- 不提交 API Key、密码、Cookie、内部 URL 或真实用户数据。
- 不为了“显得高级”引入微服务、复杂 Agent 或向量数据库。
- 不把一次 AI 输出当作事实、掌握证明或无需审核的架构决策。
- 不绕过测试、权限和来源追踪来追求 Demo 速度。
- 不修改产品设计文档来掩盖实现偏差；发现冲突应标记并请求确认。
- 不在没有明确授权时大规模重构、删除文件或改变核心技术选型。

## 开发流程

1. 阅读 `docs/PRD.md`、`docs/ARCHITECTURE.md` 和相关模块文档。
2. 将任务拆成一个可验证的小目标，确认是否处于当前 Roadmap 阶段。
3. 设计数据、API 和错误路径；如与既定架构冲突，先记录冲突。
4. 创建短生命周期分支并实现最小改动。
5. 添加或更新单元测试、集成测试或端到端验收步骤。
6. 本地执行构建、测试和必要的 Docker 验证。
7. 检查敏感信息、文档状态和 Git diff。
8. 使用规范 Commit 提交，通过 Pull Request 或等价审查合并。
9. 更新路线图、变更记录和当前实现说明。

## 当前实现与未来计划的边界

截至本文档创建时，项目仍处于产品与技术架构确定阶段。目录、接口和数据模型是实现约定，不表示对应代码已经存在。HMDP 完整 Demo、AI Provider、资料解析、知识地图和评估闭环均属于后续开发任务。
