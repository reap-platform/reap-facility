import React from 'react'
import { Form, Input, Modal, Button, Select } from 'antd'

const { Option } = Select
const FormItem = Form.Item

const formItemLayout = {
  labelCol: {
    xs: { span: 6 },
  },
  wrapperCol: {
    xs: { span: 12 },
  },
}

const Component = ({
  form, dispatch, showCreateModal, applications,
}) => {
  const { getFieldDecorator } = form
  const options = applications && applications.map(a => <Option key={a.id} value={a.systemCode}>{a.name}/{a.systemCode}</Option>)
  options.unshift(<Option key="reap" value="reap">平台/reap</Option>)
  return (
    <div>
      <Button type="primary"
        icon="plus"
        onClick={() => (dispatch({ type: 'REAPFA0001/setState', showCreateModal: true }))}
      >新增</Button>
      <Modal title="新建参数"
        visible={showCreateModal}
        onOk={() => {
          form.validateFieldsAndScroll((err, values) => {
            if (!err) {
              dispatch({ type: 'REAPFA0001/create', func: values, form })
            }
          })
        }}
        onCancel={() => {
          dispatch({ type: 'REAPFA0001/setState', showCreateModal: false })
          form.resetFields()
        }}
      >
        <Form>
          <FormItem
            {...formItemLayout}
            label="归属系统"
          >
            {getFieldDecorator('systemCode', {
            rules: [{
              required: true, message: '请选择参数归属系统',
            }],
          })(<Select showSearch placeholder="请选择系统" >{options}</Select>)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="环境"
          >
            {getFieldDecorator('profile', {
            rules: [{
              required: true, message: '参数归属环境（profile）',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="标签"
          >
            {getFieldDecorator('label', {
            rules: [{
              required: true, message: '请输入参数标签',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="参数名"
          >
            {getFieldDecorator('name', {
            rules: [{
              required: true, message: '请输入参数名',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="参数值"
          >
            {getFieldDecorator('value', {
            rules: [{
              required: true, message: '请输入参数值',
            }],
          })(<Input />)}
          </FormItem>
        </Form>
      </Modal>
    </div>
  )
}
export default Form.create()(Component)
