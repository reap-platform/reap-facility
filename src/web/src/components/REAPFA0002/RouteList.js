import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button, Card } from 'antd'
import EditableCell from '../EditableCell'
import RouteForm from './RouteForm'

const formItemLayout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 16,
  },
}

const Component = ({
  page, dispatch, showCreateModal, loading, applications,
}) => {
  const { Item } = Form
  const onCellChange = (key, dataIndex) => {
    return (value) => {
      const update = page.content.find(o => o.id === key)
      update[dataIndex] = value
      dispatch({ type: 'REAPFA0002/update', data: update })
    }
  }
  const columns = [
    {
      title: '路由名称',
      dataIndex: 'name',
      width: '30%',
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
      width: '30%',
      key: 'path',
      render: (text, record) => (
        <EditableCell
          length={30}
          value={text}
          onChange={onCellChange(record.id, 'path')}
        />
      ),
    },
    {
      title: '归属系统',
      dataIndex: 'systemCode',
      width: '30%',
      key: 'systemCode',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'systemCode')}
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
          <Item label="系统码" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    systemCode: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
      </Row>
      <Row>
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
        <RouteForm showCreateModal={showCreateModal} dispatch={dispatch} applications={applications} />
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
