# 车辆维修管理系统数据库设计

本数据库设计旨在支持车辆维修管理系统的全面功能需求，包括用户管理、车辆信息、维修工单处理、材料库存、财务管理等核心业务流程。

## 数据库实体关系图

ER图文件: `ER_diagram.puml`

使用PlantUML格式设计，可通过在线工具如 [PlantUML Online Server](https://www.plantuml.com/plantuml/) 查看或导出为图片。

## 数据库表结构

数据库包含以下主要表:

1. **User** - 存储客户信息
2. **Vehicle** - 存储车辆信息，与用户关联
3. **MaintenanceStaff** - 存储维修人员信息
4. **Administrator** - 存储系统管理员信息
5. **WorkOrder** - 存储维修工单信息
6. **RepairRecord** - 存储维修记录详情
7. **Material** - 存储维修材料/零件信息
8. **MaterialUsage** - 记录材料使用情况
9. **Feedback** - 存储用户反馈
10. **PaymentRecord** - 存储付款记录
11. **StaffPayment** - 存储维修人员工资支付记录
12. **AuditLog** - 记录系统操作日志

## 数据一致性维护

数据库实现了以下触发器来确保数据一致性:

1. **update_workorder_costs** - 当添加材料使用记录时，自动更新工单的材料成本和总成本
2. **update_labor_costs** - 当更新维修记录时，自动更新工单的工时费和总成本
3. **update_material_stock** - 当使用材料时，自动减少库存数量

## 数据视图

为便于常见查询需求，数据库创建了以下视图:

1. **UserVehicleHistory** - 用户车辆维修历史记录
2. **StaffPerformance** - 维修人员绩效统计
3. **MaterialConsumptionReport** - 材料消耗报告
4. **FinancialSummary** - 财务收支汇总

## 系统工作流程

1. 用户注册并提交维修申请
2. 系统生成工单并分配给合适的维修人员
3. 维修人员接单并记录维修过程与材料使用情况
4. 系统自动计算维修费用(工时费+材料费)
5. 用户确认并支付费用
6. 管理员监控整个流程并进行数据维护

## 数据统计与分析

数据库设计支持以下统计与分析需求:

- 车型维修次数与费用统计
- 故障类型分析
- 季度成本分析
- 负面反馈筛选
- 工种任务占比分析
- 未完成工单统计
- 财务收支分析

## 实现说明

数据库SQL脚本文件: `create_tables.sql`

该脚本包含所有表的创建语句、触发器定义以及视图创建，可直接用于数据库初始化。 