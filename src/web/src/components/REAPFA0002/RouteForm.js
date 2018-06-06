import React from 'react'
import { Form, Input, Modal, Button } from 'antd'

const FormItem = Form.Item

const formItemLayout = {
  labelCol: {
    xs: { span: 6 },
  },
  wrapperCol: {
    xs: { span: 14 },
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
        onClick={() => (dispatch({ type: 'REAPFA0002/setState', showCreateModal: true }))}
      >新增</Button>
      <Modal title="新建路由"
        visible={showCreateModal}
        onOk={() => {
          form.validateFieldsAndScroll((err, values) => {
            if (!err) {
              dispatch({ type: 'REAPFA0002/create', func: values, form })
            }
          })
        }}
        onCancel={() => {
          dispatch({ type: 'REAPFA0002/setState', showCreateModal: false })
          form.resetFields()
        }}
      >
        <Form>
          <FormItem
            {...formItemLayout}
            label="路由名称"
          >
            {getFieldDecorator('name', {
            rules: [{
              required: true, message: '请输入路由名称',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="路由规则"
          >
            {getFieldDecorator('path', {
            rules: [{
              required: true, message: '请输入路由规则',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="服务ID"
          >
            {getFieldDecorator('serviceId', {
            rules: [{
              required: true, message: '请输入服务ID（归属系统）',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="转发地址"
          >
            {getFieldDecorator('url')(<Input />)}
          </FormItem>
        </Form>
      </Modal>
    </div>
  )
}
export default Form.create()(Component)
