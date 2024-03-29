# SimpleMvvm

## Git 提交规范
为了保持代码仓库的整洁和可维护性，我们建议遵循以下 Git 提交规范：

### 1. **提交前缀**

每次提交都需要在 Git log 前追加一个提交前缀，该前缀由以下部分组成：

#### **类型**: 

用来描述提交的类型，常见类型包括：
- `feat`: 新增功能
- `fix`: 修复 Bug
- `docs`: 更新文档
- `style`: 代码风格修改
- `refactor`: 代码重构
- `test`: 添加或更新测试
- `chore`: 其他非代码变更

#### **冒号**: 
分隔符。

#### **简短描述**:
简要描述本次提交的内容。

```
feat: 添加新的登录功能
fix: 修复用户注册时出现的错误
docs: 更新 README 文件
style: 统一代码风格
refactor: 重构代码结构
test: 添加单元测试
chore: 更新依赖库
```

### 2.**提交信息**
除了提交前缀之外，您还可以添加更详细的提交信息，
例如：
- 详细描述本次提交的变更内容
- 相关问题或 issue 编号
- 代码审查人

```
feat: 添加新的登录功能
* 支持使用邮箱或手机号码登录
* 修复了登录页面的一些错误
Refs: #1234
```
