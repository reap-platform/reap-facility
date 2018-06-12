import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button, Card } from 'antd'
import EditableCell from '../EditableCell'
import FunctionForm from './ConfigForm'

const formItemLayout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 16,
  },
}


const Component = ({
  page, dispatch, showCreateModal, loading,
}) => {
  const { Item } = Form
  const onCellChange = (key, dataIndex) => {
    return (value) => {
      const update = page.content.find(o => o.id === key)
      update[dataIndex] = value
      dispatch({ type: 'REAPFA0001/update', func: update })
    }
  }
  const columns = [
    {
      title: '应用',
      dataIndex: 'application',
      width: '10%',
      key: 'application',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'application')}
        />
      ),
    },
    {
      title: '环境',
      dataIndex: 'profile',
      width: '10%',
      key: 'profile',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'profile')}
        />
      ),
    },
    {
      title: '标签',
      dataIndex: 'label',
      width: '10%',
      key: 'label',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'label')}
        />
      ),
    },
    {
      title: '参数名',
      dataIndex: 'name',
      width: '30%',
      key: 'name',
      render: (text, record) => (
        <EditableCell
          length={50}
          value={text}
          onChange={onCellChange(record.id, 'name')}
        />
      ),
    },
    {
      title: '参数值',
      width: '30%',
      dataIndex: 'value',
      key: 'value',
      render: (text, record) => (
        <EditableCell
          length={50}
          value={text}
          onChange={onCellChange(record.id, 'value')}
        />
      ),
    },
    {
      title: '操作',
      width: '10%',
      dataIndex: 'operation',
      render: (text, record) => {
        return (
          page && page.content && page.content.length > 0 ?
            (
              <Popconfirm title="确认删除?" onConfirm={() => dispatch({ type: 'REAPFA0001/delete', id: record.id })}>
                <a href="#">删除</a>
              </Popconfirm>
            ) : null
        )
      },
    },
  ]

  return (
    <Card title="参数管理" bordered={false}>
      <Row>
        <Col md={6} sm={24}>
          <Item label="归属系统" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    application: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item label="环境" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    profile: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item label="标签" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    label: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
      </Row>
      <Row>
        <Col md={6} sm={24}>
          <Item label="参数名" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    name: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item label="参数值" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item {...{ wrapperCol: { span: 16, push: 3 } }}>
            <Button
              type="primary"
              htmlType="button"
              icon="search"
              onClick={
                () => dispatch({ type: 'REAPFA0001/query' })
              }
            >
            查询
            </Button>
          </Item>
        </Col>
      </Row>
      <Row>
        <FunctionForm showCreateModal={showCreateModal} dispatch={dispatch} />
      </Row>
      <Row>
        <Table dataSource={page && page.content}
          pagination={{
            total: page && page.totalElements,
            showTotal: total => `总记录数 ${total} `,
            onChange: (number, size) => (dispatch({ type: 'REAPFA0001/query', page: number - 1, size })),
        }}
          rowKey="id"
          columns={columns}
          loading={loading}
          bordered
        />
      </Row>
    </Card>
  )
}
export default Component
