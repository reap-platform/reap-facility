import React from 'react'
import { Form, Input, Modal, Button } from 'antd'

const FUNCTION_CODE = 'REAPFA0003'

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
      <Button type="primary" icon="plus" onClick={() => (dispatch({ type: `${FUNCTION_CODE}/setState`, showCreateModal: true }))} >新增</Button>
      <Modal title="新建应用"
        visible={showCreateModal}
        onOk={() => {
          form.validateFieldsAndScroll((err, values) => {
            if (!err) {
              dispatch({ type: `${FUNCTION_CODE}/create`, data: values, form })
            }
          })
        }}
        onCancel={() => {
          dispatch({ type: `${FUNCTION_CODE}/setState`, showCreateModal: false })
          form.resetFields()
        }}
      >
        <Form>
          <FormItem
            {...formItemLayout}
            label="应用名称"
          >
            {getFieldDecorator('name', {
            rules: [{
              required: true, message: '请输入应用名称',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="系统码"
          >
            {getFieldDecorator('systemCode', {
            rules: [{
              required: true, message: '请输入系统码',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="负责人"
          >
            {getFieldDecorator('owner', {
            rules: [{
              required: true, message: '请输入负责人',
            }],
          })(<Input />)}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="备注"
          >
            {getFieldDecorator('remark')(<Input />)}
          </FormItem>
        </Form>
      </Modal>
    </div>
  )
}
export default Form.create()(Component)
