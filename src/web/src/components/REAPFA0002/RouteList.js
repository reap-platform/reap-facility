import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button, Card } from 'antd'
import EditableCell from '../EditableCell'
import FunctionForm from './RouteForm'

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
      dispatch({ type: 'REAPFA0002/update', func: update })
    }
  }
  const columns = [
    {
      title: '路由名称',
      dataIndex: 'name',
      width: '15%',
      key: 'name',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'name')}
        />
      ),
    },
    {
      title: '路由规则',
      dataIndex: 'path',
      width: '25%',
      key: 'path',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'path')}
        />
      ),
    },
    {
      title: '服务ID(serviceId)',
      dataIndex: 'serviceId',
      width: '25%',
      key: 'serviceId',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'serviceId')}
        />
      ),
    },
    {
      title: '转发地址(默认通过 serviceId 查找服务地址)',
      dataIndex: 'url',
      width: '30%',
      key: 'url',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'url')}
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
              <Popconfirm title="确认删除?" onConfirm={() => dispatch({ type: 'REAPFA0002/delete', id: record.id })}>
                <a href="#">删除</a>
              </Popconfirm>
            ) : null
        )
      },
    },
  ]

  return (
    <Card title="路由管理" bordered={false}>
      <Row>
        <Col md={6} sm={24}>
          <Item label="路由名称" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    name: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item label="路由规则" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    path: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item label="服务ID" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    serviceId: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
      </Row>
      <Row>
        <Col md={6} sm={24}>
          <Item label="转发地址" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    path: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6} sm={24}>
          <Item {...{ wrapperCol: { span: 16, push: 2 } }}>
            <Button
              type="primary"
              htmlType="button"
              icon="search"
              onClick={
                () => dispatch({ type: 'REAPFA0002/query' })
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
            onChange: (number, size) => (dispatch({ type: 'REAPFA0002/query', page: number - 1, size })),
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
