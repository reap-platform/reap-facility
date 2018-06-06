import React from 'react'
import { Form, Input, Modal, Button } from 'antd'

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
  form, dispatch, showCreateModal,
}) => {
  const { getFieldDecorator } = form


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
            label="应用"
          >
            {getFieldDecorator('application', {
            rules: [{
              required: true, message: '请输入',
            }],
          })(<Input />)}
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
