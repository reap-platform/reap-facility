import React from 'react'
import { Form, Input, Modal, Button, Select } from 'antd'

const { Option } = Select

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
  form, dispatch, showCreateModal, applications,
}) => {
  const { getFieldDecorator } = form
  const options = applications && applications.map(a => <Option key={a.id} value={a.systemCode}>{a.name}/{a.systemCode}</Option>)

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
              dispatch({ type: 'REAPFA0002/create', data: values, form })
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
            label="归属系统"
          >
            {getFieldDecorator('systemCode', {
            rules: [{
              required: true, message: '请选择参数归属系统',
            }],
          })(<Select showSearch placeholder="请选择系统" >{options}</Select>)}
          </FormItem>

        </Form>
      </Modal>
    </div>
  )
}
export default Form.create()(Component)
